package com.fva.restaurentservice.enums;

/**
 * Represents the admin approval status of a restaurant.
 */
public enum ApprovalStatus {

    /**
     * Waiting for admin approval.
     */
    PENDING,

    /**
     * Approved and allowed to operate.
     */
    APPROVED,

    /**
     * Registration rejected by admin.
     */
    REJECTED,

    /**
     * Restaurant suspended after approval.
     */
    SUSPENDED
}