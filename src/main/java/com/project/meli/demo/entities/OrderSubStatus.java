package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotSubStatusException;

public enum OrderSubStatus {
    HANDLING_NULL(1, OrderStatus.HANDLING, "Le notificamos al vendedor sobre tu compra"),
    MANUFACTURING(2, OrderStatus.HANDLING, "El vendedor tendrá listo tu producto pronto y comenzará el envío"),
    READY_TO_PRINT(1, OrderStatus.READY_TO_SHIP, "El vendedor está preparando tu paquete"),
    PRINTED(2, OrderStatus.READY_TO_SHIP, "El vendedor debe despachar tu paquete"),
    SHIPPED_NULL(1, OrderStatus.SHIPPED, "En Camino"),
    SOON_DELIVER(2, OrderStatus.SHIPPED, "Pronto a ser entregado"),
    WAITING_FOR_WITHDRAWAL(3, OrderStatus.SHIPPED, "En agencia"),
    DELIVERED_NULL(1, OrderStatus.DELIVERED, "Entregado"),
    LOST(1, OrderStatus.NOT_DELIVERED, "Perdido"),
    STOLEN(2, OrderStatus.NOT_DELIVERED, "Robado");

    private Integer order;
    private OrderStatus status;
    private String message;

    OrderSubStatus(final Integer order, final OrderStatus status, final String message) {
        this.order = order;
        this.status = status;
        this.message = message;
    }

    public static OrderSubStatus wrapperValueOf(final String subStatusName) {
        try {
            return OrderSubStatus.valueOf(subStatusName);
        } catch (Exception ex) {
            throw new NotSubStatusException("Status not found");
        }
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Integer getOrder() {
        return order;
    }
}
