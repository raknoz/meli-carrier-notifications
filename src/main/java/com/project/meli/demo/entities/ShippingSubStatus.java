package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotSubStatusException;

public enum ShippingSubStatus {
    HANDLING_NULL(1, ShippingStatus.HANDLING, "Le notificamos al vendedor sobre tu compra"),
    MANUFACTURING(2, ShippingStatus.HANDLING, "El vendedor tendrá listo tu producto pronto y comenzará el envío"),
    READY_TO_PRINT(1, ShippingStatus.READY_TO_SHIP, "El vendedor está preparando tu paquete"),
    PRINTED(2, ShippingStatus.READY_TO_SHIP, "El vendedor debe despachar tu paquete"),
    SHIPPED_NULL(1, ShippingStatus.SHIPPED, "En Camino"),
    SOON_DELIVER(2, ShippingStatus.SHIPPED, "Pronto a ser entregado"),
    WAITING_FOR_WITHDRAWAL(3, ShippingStatus.SHIPPED, "En agencia"),
    DELIVERED_NULL(1, ShippingStatus.DELIVERED, "Entregado"),
    LOST(1, ShippingStatus.NOT_DELIVERED, "Perdido"),
    STOLEN(2, ShippingStatus.NOT_DELIVERED, "Robado");

    private Integer order;
    private ShippingStatus status;
    private String message;

    ShippingSubStatus(final Integer order, final ShippingStatus status, final String message) {
        this.order = order;
        this.status = status;
        this.message = message;
    }

    public static ShippingSubStatus wrapperValueOf(final String subStatusName) {
        try {
            return ShippingSubStatus.valueOf(subStatusName);
        } catch (RuntimeException rex) {
            throw new NotSubStatusException("Status not found");
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
}
