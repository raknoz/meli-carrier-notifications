package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;

public enum ShippingStatus {
    HANDLING,
    READY_TO_SHIP,
    SHIPPED,
    DELIVERED,
    NOT_DELIVERED;

    public static ShippingStatus wrapperValueOf(final String statusName) {
        try {
            return ShippingStatus.valueOf(statusName);
        } catch (RuntimeException rex) {
            throw new NotStatusException(String.format("Status %s not found", statusName));
        }
    }

    @Override
    public String toString() {
        return super.name().replaceAll("_", "");
    }
}
