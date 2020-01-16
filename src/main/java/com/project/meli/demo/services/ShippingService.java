package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.ShippingStatisticsResponseDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingHistoricalEntity;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatisticEntity;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;
import com.project.meli.demo.repositories.ShippingRepository;
import com.project.meli.demo.repositories.ShippingStatisticRepository;
import com.project.meli.demo.repositories.StatusRepository;
import com.project.meli.demo.util.MapperUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

import static com.project.meli.demo.util.DateUtils.parseStringToLocalDateTime;

@Service
public class ShippingService {
    private final Logger logger = LoggerFactory.getLogger(ShippingService.class);
    private final ShippingRepository shippingRepository;
    private final StatusRepository statusRepository;
    private final ShippingStatisticRepository shippingStatisticRepository;

    public ShippingService(final ShippingRepository shippingRepository, final StatusRepository statusRepository,
                           final ShippingStatisticRepository shippingStatisticRepository) {
        this.shippingRepository = shippingRepository;
        this.statusRepository = statusRepository;
        this.shippingStatisticRepository = shippingStatisticRepository;
    }

    /**
     * Service to get the last status of a Shipping.
     *
     * @param request Object with a identifier and the status of the shipping.
     * @return A string that contain the message of the last sub-status.
     */
    public String packages(final ShippingRequestDTO request) {
        final ShippingHistoricalEntity shippingHistorical = shippingQueryRegister(request.getId());
        final ShippingMovement shippingMovement = sortingByShippingStatus(request);
        logger.debug(String.format("Shipping code: %s, is in status: %s -> %s", request.getId(),
                shippingMovement.getStatus().getDescription(), shippingMovement.getSubStatus().getDescription()));
        shippingQueryRegister(shippingHistorical, shippingMovement);
        return shippingMovement.getMessage();
    }

    /**
     * Service to save information when start the process.
     *
     * @param shippingCode Id of the shipping.
     * @return Object persisted on the dataBase.
     */
    public ShippingHistoricalEntity shippingQueryRegister(final String shippingCode) {
        logger.info(String.format("Save query of shipping code: %s", shippingCode));
        return shippingRepository.save(new ShippingHistoricalEntity(shippingCode, null, null));
    }

    /**
     * Service to save in historical_query_shipping, the information about the last status.
     *
     * @param shippingHistorical Object to update status and save it.
     * @param shippingMovement   Entity with the last status of the Shipping.
     */
    public void shippingQueryRegister(final ShippingHistoricalEntity shippingHistorical, final ShippingMovement shippingMovement) {
        logger.info(String.format("Update register of shipping code: %s", shippingHistorical.getShippingCode()));
        shippingHistorical.setStatus(shippingMovement.getStatus().getDescription());
        shippingHistorical.setSubStatus(shippingMovement.getSubStatus().getDescription());
        shippingHistorical.setDateUpdate(LocalDateTime.now());
        shippingRepository.save(shippingHistorical);
    }

    /**
     * Method to return a list of Shipping historical records in pages.
     *
     * @param fromDateStr Initial date to filter the report.
     * @param toDateStr   Final date to filter the report.
     * @return Object with results of Statistics
     */
    public ShippingStatisticsResponseDTO getStatisticsByDate(final String fromDateStr, final String toDateStr) {
        final LocalDate fromDate = parseStringToLocalDateTime(fromDateStr);
        final LocalDate toDate = parseStringToLocalDateTime(toDateStr);
        final ShippingStatisticEntity response = shippingStatisticRepository.getStatisticsByDate(fromDate, toDate);
        return MapperUtils.convertEntityToDto(response, fromDate, toDate);
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
                .max(Comparator.comparing((ShippingMovement sm) -> sm.getStatus().getOrder())
                        .thenComparing(sm -> sm.getSubStatus().getOrder()))
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
                    String.format("Sub-status '%s' does not belong to '%s' status ", subStatus, shippingStatus.getDescription()));
        }
        return shippingSubStatus.get();
    }
}
