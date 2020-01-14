package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingHistoricalRecord;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.repositories.ShippingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingService {
    private final Map<ShippingStatus, ShippingSubStatus> nullMapping;
    private final ShippingRepository shippingRepository;

    public ShippingService(final ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
        nullMapping = new HashMap<>();
        nullMapping.put(ShippingStatus.HANDLING, ShippingSubStatus.HANDLING_NULL);
        nullMapping.put(ShippingStatus.SHIPPED, ShippingSubStatus.SHIPPED_NULL);
        nullMapping.put(ShippingStatus.DELIVERED, ShippingSubStatus.DELIVERED_NULL);
    }

    /**
     * Service to get the last status of a Shipping.
     *
     * @param request Object with a identifier and the status of the shipping.
     * @return A string that contain the message of the last sub-status.
     */
    public String packages(final ShippingRequestDTO request) {
        ShippingMovement shippingMovement = request.getInputs().stream()
                .map(this::getOrderMovement)
                .max(Comparator.comparing((ShippingMovement sm) -> sm.getStatus().getOrder())
                        .thenComparing(sm -> sm.getSubStatus().getOrder()))
                .orElseThrow(() -> new NotStatusException("Status are empty"));
        shippingQueryRegister(request.getId(), shippingMovement);
        return shippingMovement.getSubStatus().getMessage();
    }

    /**
     * Service to save in historical_query_shipping, the information about the request.
     *
     * @param shippingCode Code to identifier the Shipping.
     * @param shippingMovement Entity with the last status of the Shipping.
     */
    public void shippingQueryRegister(final String shippingCode, final ShippingMovement shippingMovement){
        final ShippingHistoricalRecord historicalRecord = new ShippingHistoricalRecord();
        historicalRecord.setDateTime(LocalDateTime.now());
        historicalRecord.setShippingCode(shippingCode);
        historicalRecord.setStatus(shippingMovement.getStatus().name());
        historicalRecord.setSubStatus(shippingMovement.getSubStatus().name());
        shippingRepository.save(historicalRecord);
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
        final ShippingStatus status = ShippingStatus.wrapperValueOf(stateShippingRequestDTO.getStatus().toUpperCase());
        return new ShippingMovement(status, getSubStatus(stateShippingRequestDTO.getSubstatus(), status));
    }

    /**
     * Method that obtain sub-status by name and validate the relationship with the status.
     *
     * @param subStatus The name of sub-status.
     * @param shippingStatus Object with the status.
     * @return Am Object that contain information of sub-status.
     */
    private ShippingSubStatus getSubStatus(final String subStatus, final ShippingStatus shippingStatus) {
        ShippingSubStatus shippingSubStatus;
        if (StringUtils.isNotBlank(subStatus)) {
            shippingSubStatus = ShippingSubStatus.wrapperValueOf(subStatus.toUpperCase());
        } else {
            if (!nullMapping.containsKey(shippingStatus)) {
                throw new NotStatusException("Status Not found");
            }
            shippingSubStatus = nullMapping.get(shippingStatus);
        }
        if (!shippingStatus.equals(shippingSubStatus.getStatus())) {
            throw new BadRequestException("Sub-status does not belong to this status");
        }
        return shippingSubStatus;
    }
}
