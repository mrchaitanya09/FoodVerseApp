package com.fva.orderservice.enums;

/**
 * Represents the lifecycle of an order.
 */
public enum OrderStatus {

    /**
     * Order placed successfully.
     */
    CREATED,

    /**
     * Restaurant has accepted the order.
     */
    CONFIRMED,

    /**
     * Food preparation has started.
     */
    PREPARING,

    /**
     * Order is ready for pickup.
     */
    READY,

    /**
     * Delivery partner has picked up the order.
     */
    PICKED_UP,

    /**
     * Order delivered successfully.
     */
    DELIVERED,

    /**
     * Order cancelled.
     */
    CANCELLED
}