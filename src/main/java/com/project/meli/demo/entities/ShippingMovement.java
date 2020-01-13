package com.project.meli.demo.entities;

public class ShippingMovement {
    private final ShippingStatus status;
    private final ShippingSubStatus subStatus;

    public ShippingMovement(final ShippingStatus status, final ShippingSubStatus subStatus) {
        this.status = status;
        this.subStatus = subStatus;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public ShippingSubStatus getSubStatus() {
        return subStatus;
    }
}
