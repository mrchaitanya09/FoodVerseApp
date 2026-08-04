package com.fva.paymentservice.entity;

import com.fva.paymentservice.enums.RefundStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "refunds",
        indexes = {
                @Index(name = "idx_refund_payment", columnList = "payment_id"),
                @Index(name = "idx_refund_number", columnList = "refund_number"),
                @Index(name = "idx_refund_status", columnList = "refund_status")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_refund_number",
                        columnNames = "refund_number"
                )
        }
)
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Parent Payment
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "payment_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_refund_payment")
    )
    private Payment payment;

    /**
     * Example: REF202600001
     */
    @NotBlank(message = "Refund number is required")
    @Column(
            name = "refund_number",
            nullable = false,
            length = 30,
            updatable = false
    )
    private String refundNumber;

    /**
     * Amount refunded.
     */
    @NotNull(message = "Refund amount is required")
    @DecimalMin("0.00")
    @Column(
            name = "refund_amount",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal refundAmount;

    /**
     * Reason for refund.
     */
    @NotBlank(message = "Refund reason is required")
    @Column(
            name = "refund_reason",
            nullable = false,
            length = 500
    )
    private String refundReason;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "refund_status",
            nullable = false,
            length = 20
    )
    @Builder.Default
    private RefundStatus refundStatus = RefundStatus.PENDING;

    /**
     * Payment Gateway Refund Id.
     */
    @Column(name = "gateway_refund_id", length = 100)
    private String gatewayRefundId;

    /**
     * Gateway response.
     */
    @Lob
    @Column(name = "gateway_response", columnDefinition = "LONGTEXT")
    private String gatewayResponse;

    /**
     * Refund completion time.
     */
    @Column(name = "refunded_at")
    private LocalDateTime refundedAt;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}