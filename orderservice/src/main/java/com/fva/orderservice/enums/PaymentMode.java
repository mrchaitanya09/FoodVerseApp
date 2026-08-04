package com.fva.orderservice.enums;

/**
 * Represents the payment method used by the customer.
 */
public enum PaymentMode {

    /**
     * Cash On Delivery.
     */
    COD,

    /**
     * Credit/Debit Card.
     */
    CARD,

    /**
     * UPI Payment.
     */
    UPI,

    /**
     * Wallet Payment.
     */
    WALLET
}