package com.project.meli.demo.services;

import com.project.meli.demo.dtos.PackageRequestDTO;
import com.project.meli.demo.dtos.StateOrderRequestDTO;
import com.project.meli.demo.entities.OrderMovement;
import com.project.meli.demo.entities.OrderStatus;
import com.project.meli.demo.entities.OrderSubStatus;
import com.project.meli.demo.exceptions.BadRequestException;
import com.project.meli.demo.exceptions.NotStatusException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class PackageService {
    private final Map<OrderStatus, OrderSubStatus> nullMapping;

    public PackageService() {
        nullMapping = new HashMap<>();
        nullMapping.put(OrderStatus.HANDLING, OrderSubStatus.HANDLING_NULL);
        nullMapping.put(OrderStatus.SHIPPED, OrderSubStatus.SHIPPED_NULL);
        nullMapping.put(OrderStatus.DELIVERED, OrderSubStatus.DELIVERED_NULL);
    }

    public String packages(final PackageRequestDTO request) {
        OrderMovement orderMovement = request.getInputs().stream()
                .map(this::getOrderMovement)
                .max(Comparator.comparing((OrderMovement om) -> om.getStatus().getOrder())
                        .thenComparing(om -> om.getSubStatus().getOrder()))
                .orElseThrow(() -> new NotStatusException("Status are empty"));
        return orderMovement.getSubStatus().getMessage();
    }

    private OrderMovement getOrderMovement(final StateOrderRequestDTO stateOrderRequestDTO) {
        if (StringUtils.isBlank(stateOrderRequestDTO.getStatus())) {
            throw new BadRequestException("Status not defined");
        }
        final OrderStatus status = OrderStatus.wrapperValueOf(stateOrderRequestDTO.getStatus().toUpperCase());
        return new OrderMovement(status, getSubStatus(stateOrderRequestDTO.getSubstatus(), status));
    }

    private OrderSubStatus getSubStatus(final String subStatus, final OrderStatus orderStatus) {
        OrderSubStatus orderSubStatus;
        if (StringUtils.isNotBlank(subStatus)) {
            orderSubStatus = OrderSubStatus.wrapperValueOf(subStatus.toUpperCase());
        } else {
            if (!nullMapping.containsKey(orderStatus)) {
                throw new NotStatusException("Status Not found");
            }
            orderSubStatus = nullMapping.get(orderStatus);
        }
        if (!orderStatus.equals(orderSubStatus.getStatus())) {
            throw new BadRequestException("Sub Status does not belong to this status");
        }
        return orderSubStatus;
    }
}
