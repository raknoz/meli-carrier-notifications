package com.project.meli.demo.entities;

public class OrderMovement {
    private final OrderStatus status;
    private final OrderSubStatus subStatus;

    public OrderMovement(final OrderStatus status, final OrderSubStatus subStatus) {
        this.status = status;
        this.subStatus = subStatus;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderSubStatus getSubStatus() {
        return subStatus;
    }
}
