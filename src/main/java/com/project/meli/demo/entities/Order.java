package com.project.meli.demo.entities;

public class Order {
    private final Long packageId;
    private final OrderMovement orderMovement;

    public Order(final Long packageId, final OrderMovement orderMovement) {
        this.packageId = packageId;
        this.orderMovement = orderMovement;
    }

    public Long getPackageId() {
        return packageId;
    }

    public OrderMovement getOrderMovement() {
        return orderMovement;
    }
}
