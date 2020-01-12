package com.project.meli.demo.entities;

import java.time.LocalDateTime;

public class Order {
    private final Long id;
    private final String orderId;
    private final OrderMovement orderMovement;
    private final LocalDateTime dateTime;

    public Order(final Long id, final String packageId, final OrderMovement orderMovement, final LocalDateTime dateTime) {
        this.id = id;
        this.orderId = packageId;
        this.orderMovement = orderMovement;
        this.dateTime = dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderMovement getOrderMovement() {
        return orderMovement;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getId() {
        return id;
    }
}
