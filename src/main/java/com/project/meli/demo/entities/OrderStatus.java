package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatus {
    HANDLING(1),
    READY_TO_SHIP(2),
    SHIPPED(3),
    DELIVERED(4),
    NOT_DELIVERED(5);

    private Integer order;

    private static Map<String, OrderStatus> orderStatusMapping = new HashMap<>();
    static {
        orderStatusMapping.put(HANDLING.name(), HANDLING);
        orderStatusMapping.put(READY_TO_SHIP.name(), READY_TO_SHIP);
        orderStatusMapping.put(SHIPPED.name(), SHIPPED);
        orderStatusMapping.put(DELIVERED.name(), DELIVERED);
        orderStatusMapping.put(NOT_DELIVERED.name(), NOT_DELIVERED);
    }

    OrderStatus(final Integer order) {
        this.order = order;
    }

    public static OrderStatus getOrderStatus(final String statusName) {
        if (orderStatusMapping.get(statusName) == null) {
            throw new NotStatusException("Status not found");
        }
        return orderStatusMapping.get(statusName);
    }

    public Integer getOrder() {
        return order;
    }
}
