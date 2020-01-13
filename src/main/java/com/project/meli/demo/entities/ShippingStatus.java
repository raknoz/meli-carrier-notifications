package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;

public enum ShippingStatus {
    HANDLING(1),
    READY_TO_SHIP(2),
    SHIPPED(3),
    DELIVERED(4),
    NOT_DELIVERED(5);

    private Integer order;

    ShippingStatus(final Integer order) {
        this.order = order;
    }

    public static ShippingStatus wrapperValueOf(final String statusName) {
        try {
            return ShippingStatus.valueOf(statusName);
        } catch (RuntimeException rex) {
            throw new NotStatusException("Status not found");
        }
    }

    public Integer getOrder() {
        return order;
    }
}
