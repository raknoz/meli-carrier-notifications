package com.project.meli.demo.entities;

import com.project.meli.demo.exceptions.NotStatusException;
import com.project.meli.demo.exceptions.NotSubStatusException;

import java.util.HashMap;
import java.util.Map;

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
    private static Map<String, OrderSubStatus> orderSubStatusMapping = new HashMap<>();

    OrderSubStatus(final Integer order, final OrderStatus status, final String message) {
        this.order = order;
        this.status = status;
        this.message = message;
    }

    static {
        orderSubStatusMapping.put(HANDLING_NULL.name(), HANDLING_NULL);
        orderSubStatusMapping.put(MANUFACTURING.name(), MANUFACTURING);
        orderSubStatusMapping.put(READY_TO_PRINT.name(), READY_TO_PRINT);
        orderSubStatusMapping.put(PRINTED.name(), PRINTED);
        orderSubStatusMapping.put(SHIPPED_NULL.name(), SHIPPED_NULL);
        orderSubStatusMapping.put(SOON_DELIVER.name(), SOON_DELIVER);
        orderSubStatusMapping.put(WAITING_FOR_WITHDRAWAL.name(), WAITING_FOR_WITHDRAWAL);
        orderSubStatusMapping.put(DELIVERED_NULL.name(), DELIVERED_NULL);
        orderSubStatusMapping.put(LOST.name(), LOST);
        orderSubStatusMapping.put(STOLEN.name(), STOLEN);
    }

    public static OrderSubStatus getOrderSubStatus(final String subStatusName) {
        if (orderSubStatusMapping.get(subStatusName) == null) {
            throw new NotSubStatusException("SubStatus not found");
        }
        return orderSubStatusMapping.get(subStatusName);
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
