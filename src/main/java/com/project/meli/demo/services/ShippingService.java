package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingHistoricalRecord;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.repositories.ShippingRepository;
import com.project.meli.demo.repositories.StatusRepository;
import com.project.meli.demo.util.PagedResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class ShippingService {
    private final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    private final String HEALTH_MESSAGE = "I'm alive";
    private final ShippingRepository shippingRepository;
    private final StatusRepository statusRepository;

    public ShippingService(final ShippingRepository shippingRepository, final StatusRepository statusRepository) {
        this.shippingRepository = shippingRepository;
        this.statusRepository = statusRepository;
    }

    /**
     * Service to get the last status of a Shipping.
     *
     * @param request Object with a identifier and the status of the shipping.
     * @return A string that contain the message of the last sub-status.
     */
    public String packages(final ShippingRequestDTO request) {
        final ShippingMovement shippingMovement = sortingByShippingStatus(request);
        logger.info(String.format("Save register of shipping code: %s", request.getId()));
        shippingQueryRegister(request.getId(), shippingMovement);

        logger.debug(String.format("Shipping code: %s, is in status: %s -> %s", request.getId(), shippingMovement.getStatus().name(),
                shippingMovement.getMessage()));
        return shippingMovement.getMessage();
    }

    /**
     * Service to save in historical_query_shipping, the information about the request.
     *
     * @param shippingCode     Code to identifier the Shipping.
     * @param shippingMovement Entity with the last status of the Shipping.
     */
    public void shippingQueryRegister(final String shippingCode, final ShippingMovement shippingMovement) {
        shippingRepository.save(new ShippingHistoricalRecord(shippingCode, shippingMovement.getStatus().name(),
                shippingMovement.getSubStatus().name()));
    }

    /**
     * Method to return a list of Shipping historical records in pages.
     *
     * @param page Number of the page to return.
     * @param size Number of elements by page.
     * @param date Date to filter the data.
     * @return List with results of Statistics
     */
    public PagedResult<ShippingHistoricalRecord> getStatisticsByDate(final Integer page, final Integer size, final String date) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<ShippingHistoricalRecord> pagedResult = shippingRepository.findAll(paging);
        return new PagedResult<>(pagedResult.getContent(), pagedResult.getTotalElements(), pagedResult.getNumberOfElements(),
                pagedResult.getTotalPages());
    }

    /**
     * Method to valid about the health of the application.
     *
     * @return String with a message.
     */
    public String getHealth() {
        return HEALTH_MESSAGE;
    }

    /**
     * Method to get the max status and sub-status of a list.
     *
     * @param request Object with the list of statues.
     * @return A object with the max status and max sub-status.
     */
    private ShippingMovement sortingByShippingStatus(final ShippingRequestDTO request) {
        return request.getInputs().stream()
                .map(this::getOrderMovement)
                .max(Comparator.comparing((ShippingMovement sm) -> sm.getStatus().getOrder())
                        .thenComparing(sm -> sm.getSubStatus().getOrder()))
                .orElseThrow(() -> new NotStatusException("Status are empty"));
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
        Optional<ShippingSubStatus> shippingSubStatus = statusRepository.getSubStatusByNameAndStatus(subStatus, shippingStatus);
        if (!shippingSubStatus.isPresent()) {
            throw new NotStatusException("Sub Status Not found");
        }
        if (!shippingStatus.equals(shippingSubStatus.get().getStatus())) {
            throw new BadRequestException("Sub-status does not belong to this status");
        }
        return shippingSubStatus.get();
    }
}
