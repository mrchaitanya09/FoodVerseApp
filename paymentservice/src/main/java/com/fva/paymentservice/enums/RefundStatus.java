package com.fva.paymentservice.enums;

/**
 * Represents the current refund status.
 */
public enum RefundStatus {

    /**
     * Refund request initiated.
     */
    PENDING,

    /**
     * Refund processed successfully.
     */
    SUCCESS,

    /**
     * Refund processing failed.
     */
    FAILED
}