package com.project.meli.demo.entities;

public enum OrderSubStatus {
    HANDLING_NULL(OrderStatus.HANDLING, "msg 1"),
    MANUFACTURING(OrderStatus.HANDLING, "msg 4"),
    READY_TO_PRINT(OrderStatus.READY_TO_SHIP, "msg 5"),
    PRINTED(OrderStatus.READY_TO_SHIP, "msg 6"),
    SHIPPED_NULL(OrderStatus.SHIPPED, "msg 2"),
    SOON_DELIVER(OrderStatus.SHIPPED, "msg 7"),
    WAITING_FOR_WITHDRAWAL(OrderStatus.SHIPPED, "msg 8"),
    DELIVERED_NULL(OrderStatus.DELIVERED, "msg 3"),
    LOST(OrderStatus.NOT_DELIVERED, "msg 9"),
    STOLEN(OrderStatus.NOT_DELIVERED, "msg 10");

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
