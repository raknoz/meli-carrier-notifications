package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;

public enum OrderStatus {
    HANDLING(1),
    READY_TO_SHIP(2),
    SHIPPED(3),
    DELIVERED(4),
    NOT_DELIVERED(5);

    private Integer order;

    OrderStatus(final Integer order) {
        this.order = order;
    }

    public static OrderStatus wrapperValueOf(final String statusName) {
        try {
            return OrderStatus.valueOf(statusName);
        } catch (Exception ex) {
            throw new NotStatusException("Status not found");
        }
    }

    public Integer getOrder() {
        return order;
    }
}
