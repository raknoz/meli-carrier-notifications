package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import com.project.meli.demo.repositories.StatusRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class ShippingService {
    private final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    private final StatusRepository statusRepository;
    private final ShippingHistoricalService shippingHistoricalService;


    public ShippingService(final ShippingHistoricalService shippingHistoricalService, final StatusRepository statusRepository) {
        this.shippingHistoricalService = shippingHistoricalService;
        this.statusRepository = statusRepository;
    }

    /**
     * Service to get the last status of a Shipping.
     *
     * @param request Object with a identifier and the status of the shipping.
     * @return A string that contain the message of the last sub-status.
     */
    public String packages(final ShippingRequestDTO request) {
        final ShippingHistoricalEntity shippingHistorical = shippingHistoricalService.shippingQueryRegister(request.getId());
        final ShippingMovement shippingMovement = sortingByShippingStatus(request);
        logger.debug(String.format("Shipping code: %s, is in status: %s -> %s", request.getId(),
                shippingMovement.getStatus().toString(), shippingMovement.getSubStatus().toString()));
        shippingHistoricalService.shippingQueryRegister(shippingHistorical, shippingMovement);
        return shippingMovement.getMessage();
    }

    /**
     * Method to get the max status and sub-status of a list.
     *
     * @param request Object with the list of statues.
     * @return A object with the max status and max sub-status.
     */
    private ShippingMovement sortingByShippingStatus(final ShippingRequestDTO request) {
        logger.info(String.format("Init to get status of shipping code: %s", request.getId()));
        return request.getInputs().stream()
                .map(this::getOrderMovement)
                .max(Comparator.comparing((ShippingMovement sm) -> sm.getStatus().ordinal())
                        .thenComparing(sm -> sm.getSubStatus().ordinal()))
                .orElseThrow(() -> new NotStatusException("Statues are empty"));
    }

    /**
     * Method that validate the status and obtain the status-entity by name.
     *
     * @param stateShippingRequestDTO Object with a identifier and the status of the shipping.
     * @return A object with the entities status and sub-status.
     */
    private ShippingMovement getOrderMovement(final StateShippingRequestDTO stateShippingRequestDTO) {
        if (StringUtils.isBlank(stateShippingRequestDTO.getStatus())) {
            throw new BadRequestException("Status not defined");
        }
        final ShippingStatus status = statusRepository.getStatusByName(stateShippingRequestDTO.getStatus());
        return new ShippingMovement(status, getSubStatus(stateShippingRequestDTO.getSubstatus(), status));
    }

    /**
     * Method that obtain sub-status by name and validate the relationship with the status.
     *
     * @param subStatus      The name of sub-status.
     * @param shippingStatus Object with the status.
     * @return Am Object that contain information of sub-status.
     */
    private ShippingSubStatus getSubStatus(final String subStatus, final ShippingStatus shippingStatus) {
        Optional<ShippingSubStatus> shippingSubStatus = statusRepository.getSubStatusByNameAndStatus(subStatus,
                shippingStatus);
        if (!shippingSubStatus.isPresent()) {
            throw new NotSubStatusException(String.format("Sub-status '%s' Not found", subStatus));
        }
        if (!shippingStatus.equals(shippingSubStatus.get().getStatus())) {
            throw new BadRequestException(
                    String.format("Sub-status '%s' does not belong to '%s' status ", subStatus, shippingStatus.toString()));
        }
        return shippingSubStatus.get();
    }
}
