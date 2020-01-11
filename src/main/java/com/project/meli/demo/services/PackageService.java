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
                .max(Comparator.comparing((OrderMovement om) -> om.getStatus().ordinal())
                        .thenComparing(om -> om.getSubStatus().ordinal()))
                .orElseThrow(() -> new NotStatusException("No data"));
        return orderMovement.getSubStatus().getMessage();
    }

    private OrderMovement getOrderMovement(final StateOrderRequestDTO stateOrderRequestDTO) {
        if (StringUtils.isBlank(stateOrderRequestDTO.getStatus())) {
            throw new BadRequestException("Datos erroneos");
        }
        final OrderStatus status = OrderStatus.valueOf(stateOrderRequestDTO.getStatus().toUpperCase());
        return new OrderMovement(status, getSubStatus(stateOrderRequestDTO.getSubstatus(), status));
    }

    private OrderSubStatus getSubStatus(final String subStatus, final OrderStatus orderStatus) {
        OrderSubStatus orderSubStatus;
        if (StringUtils.isNotBlank(subStatus)) {
            orderSubStatus = OrderSubStatus.valueOf(subStatus.toUpperCase());
        } else {
            if (!nullMapping.containsKey(orderStatus)) {
                throw new BadRequestException("Not order status");
            }
            orderSubStatus = nullMapping.get(orderStatus);
        }
        if (orderStatus.equals(orderSubStatus.getStatus())) {
            throw new BadRequestException("Sub Status does not belong to this status");
        }
        return orderSubStatus;
    }
}
