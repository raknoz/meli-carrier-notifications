package com.project.meli.demo.services;

import com.project.meli.demo.dtos.ShippingRequestDTO;
import com.project.meli.demo.dtos.StateShippingRequestDTO;
import com.project.meli.demo.entities.ShippingMovement;
import com.project.meli.demo.entities.ShippingStatus;
import com.project.meli.demo.entities.ShippingSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class ShippingService {
    private final Map<ShippingStatus, ShippingSubStatus> nullMapping;

    public ShippingService() {
        nullMapping = new HashMap<>();
        nullMapping.put(ShippingStatus.HANDLING, ShippingSubStatus.HANDLING_NULL);
        nullMapping.put(ShippingStatus.SHIPPED, ShippingSubStatus.SHIPPED_NULL);
        nullMapping.put(ShippingStatus.DELIVERED, ShippingSubStatus.DELIVERED_NULL);
    }

    public String packages(final ShippingRequestDTO request) {
        ShippingMovement shippingMovement = request.getInputs().stream()
                .map(this::getOrderMovement)
                .max(Comparator.comparing((ShippingMovement sm) -> sm.getStatus().getOrder())
                        .thenComparing(sm -> sm.getSubStatus().getOrder()))
                .orElseThrow(() -> new NotStatusException("Status are empty"));
        return shippingMovement.getSubStatus().getMessage();
    }

    private ShippingMovement getOrderMovement(final StateShippingRequestDTO stateShippingRequestDTO) {
        if (StringUtils.isBlank(stateShippingRequestDTO.getStatus())) {
            throw new BadRequestException("Status not defined");
        }
        final ShippingStatus status = ShippingStatus.wrapperValueOf(stateShippingRequestDTO.getStatus().toUpperCase());
        return new ShippingMovement(status, getSubStatus(stateShippingRequestDTO.getSubstatus(), status));
    }

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
