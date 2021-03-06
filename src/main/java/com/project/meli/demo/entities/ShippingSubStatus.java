package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotSubStatusException;

public enum ShippingSubStatus {
    HANDLING_NULL(ShippingStatus.HANDLING, "Le notificamos al vendedor sobre tu compra"),
    MANUFACTURING(ShippingStatus.HANDLING, "El vendedor tendrá listo tu producto pronto y comenzará el envío"),
    READY_TO_PRINT(ShippingStatus.READY_TO_SHIP, "El vendedor está preparando tu paquete"),
    PRINTED(ShippingStatus.READY_TO_SHIP, "El vendedor debe despachar tu paquete"),
    SHIPPED_NULL(ShippingStatus.SHIPPED, "En Camino"),
    SOON_DELIVER(ShippingStatus.SHIPPED, "Pronto a ser entregado"),
    WAITING_FOR_WITHDRAWAL(ShippingStatus.SHIPPED, "En agencia"),
    DELIVERED_NULL(ShippingStatus.DELIVERED, "Entregado"),
    LOST(ShippingStatus.NOT_DELIVERED, "Perdido"),
    STOLEN(ShippingStatus.NOT_DELIVERED, "Robado");

    private ShippingStatus status;
    private String message;

    ShippingSubStatus(final ShippingStatus status, final String message) {
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

    @Override
    public String toString() {
        return this.name().replaceAll("_", " ");
    }
}
