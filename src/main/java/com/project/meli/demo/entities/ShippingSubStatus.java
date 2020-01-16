package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotSubStatusException;

public enum ShippingSubStatus {
    HANDLING_NULL(1, null, ShippingStatus.HANDLING, "Le notificamos al vendedor sobre tu compra"),
    MANUFACTURING(2, "MANUFACTURING", ShippingStatus.HANDLING, "El vendedor tendrá listo tu producto pronto y comenzará el envío"),
    READY_TO_PRINT(1, "READY TO PRINT", ShippingStatus.READY_TO_SHIP, "El vendedor está preparando tu paquete"),
    PRINTED(2, "PRINTED", ShippingStatus.READY_TO_SHIP, "El vendedor debe despachar tu paquete"),
    SHIPPED_NULL(1, null, ShippingStatus.SHIPPED, "En Camino"),
    SOON_DELIVER(2, "SOON_DELIVER", ShippingStatus.SHIPPED, "Pronto a ser entregado"),
    WAITING_FOR_WITHDRAWAL(3, "WAITING FOR WITHDRAWAL", ShippingStatus.SHIPPED, "En agencia"),
    DELIVERED_NULL(1, null, ShippingStatus.DELIVERED, "Entregado"),
    LOST(1, "LOST", ShippingStatus.NOT_DELIVERED, "Perdido"),
    STOLEN(2, "STOLEN", ShippingStatus.NOT_DELIVERED, "Robado");

    private Integer order;
    private String description;
    private ShippingStatus status;
    private String message;

    ShippingSubStatus(final Integer order, final String description, final ShippingStatus status, final String message) {
        this.order = order;
        this.description = description;
        this.status = status;
        this.message = message;
    }

    public static ShippingSubStatus wrapperValueOf(final String subStatusName) {
        try {
            return ShippingSubStatus.valueOf(subStatusName);
        } catch (RuntimeException rex) {
            throw new NotSubStatusException(String.format("Sub-status %s not found", subStatusName));
        }
    }

    public String getMessage() {
        return message;
    }

    public ShippingStatus getStatus() {
        return status;
    }

    public Integer getOrder() {
        return order;
    }

    public String getDescription() {
        return description;
    }
}
