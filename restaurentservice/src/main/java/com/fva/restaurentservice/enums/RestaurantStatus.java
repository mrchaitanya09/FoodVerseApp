package com.fva.restaurentservice.enums;

/**
 * Represents the current operational status of a restaurant.
 */
public enum RestaurantStatus {

    /**
     * Restaurant is open and accepting orders.
     */
    OPEN,

    /**
     * Restaurant is temporarily closed (outside business hours).
     */
    CLOSED,

    /**
     * Restaurant is open but experiencing high order volume.
     */
    BUSY,

    /**
     * Restaurant is temporarily unavailable due to maintenance,
     * holidays, or other operational reasons.
     */
    TEMPORARILY_CLOSED
}