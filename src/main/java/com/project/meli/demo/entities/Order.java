package com.project.meli.demo.entities;

import java.time.LocalDateTime;

public class Order {
    private final Long id;
    private final String orderId;
    private final String status;
    private final String subStatus;
    private final LocalDateTime dateTime;

    public Order(final Long id, final String orderId, final String status, final String subStatus, final LocalDateTime dateTime) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.subStatus = subStatus;
        this.dateTime = dateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getStatus() {
        return status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Long getId() {
        return id;
    }
}
