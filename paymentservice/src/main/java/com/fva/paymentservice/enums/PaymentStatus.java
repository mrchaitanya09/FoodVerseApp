package com.fva.paymentservice.enums;

/**
 * Represents the current payment status.
 */
public enum PaymentStatus {

    /**
     * Payment initiated and waiting for processing.
     */
    PENDING,

    /**
     * Payment completed successfully.
     */
    SUCCESS,

    /**
     * Payment failed.
     */
    FAILED,

    /**
     * Payment refunded.
     */
    REFUND
}