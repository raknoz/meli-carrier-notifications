package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;

public enum ShippingStatus {
    HANDLING(1, "HANDLING"),
    READY_TO_SHIP(2, "READY TO SHIP"),
    SHIPPED(3, "SHIPPED"),
    DELIVERED(4, "DELIVERED"),
    NOT_DELIVERED(5, "NOT DELIVERED");

    private Integer order;
    private String description;

    ShippingStatus(final Integer order, final String description) {
        this.order = order;
        this.description = description;
    }

    public static ShippingStatus wrapperValueOf(final String statusName) {
        try {
            return ShippingStatus.valueOf(statusName);
        } catch (RuntimeException rex) {
            throw new NotStatusException(String.format("Status %s not found", statusName));
        }
    }

    public Integer getOrder() {
        return order;
    }

    public String getDescription() {
        return description;
    }
}
