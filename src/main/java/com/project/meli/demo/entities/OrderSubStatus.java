package com.project.meli.demo.entities;

public enum OrderSubStatus {
    HANDLING_NULL(OrderStatus.HANDLING, "Le notificamos al vendedor sobre tu compra"),
    MANUFACTURING(OrderStatus.HANDLING, "El vendedor tendrá listo tu producto pronto y comenzará el envío"),
    READY_TO_PRINT(OrderStatus.READY_TO_SHIP, "El vendedor está preparando tu paquete"),
    PRINTED(OrderStatus.READY_TO_SHIP, "El vendedor debe despachar tu paquete"),
    SHIPPED_NULL(OrderStatus.SHIPPED, "En Camino"),
    SOON_DELIVER(OrderStatus.SHIPPED, "Pronto a ser entregado"),
    WAITING_FOR_WITHDRAWAL(OrderStatus.SHIPPED, "En agencia"),
    DELIVERED_NULL(OrderStatus.DELIVERED, "Entregado"),
    LOST(OrderStatus.NOT_DELIVERED, "Perdido"),
    STOLEN(OrderStatus.NOT_DELIVERED, "Robado");

    private OrderStatus status;
    private String message;

    OrderSubStatus(final OrderStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
