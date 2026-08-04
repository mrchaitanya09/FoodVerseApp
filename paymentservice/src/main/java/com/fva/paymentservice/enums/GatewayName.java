package com.fva.paymentservice.enums;

/**
 * Supported payment gateways.
 */
public enum GatewayName {

    /**
     * Internal mock payment gateway.
     */
    MOCK,

    /**
     * Razorpay Payment Gateway.
     */
    RAZORPAY,

    /**
     * Stripe Payment Gateway.
     */
    STRIPE,

    /**
     * PayPal Payment Gateway.
     */
    PAYPAL
}