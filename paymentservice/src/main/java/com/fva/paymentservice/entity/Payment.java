package com.fva.paymentservice.entity;

import com.fva.paymentservice.enums.GatewayName;
import com.fva.paymentservice.enums.PaymentMethod;
import com.fva.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(name = "idx_payment_number", columnList = "payment_number"),
                @Index(name = "idx_payment_order", columnList = "order_id"),
                @Index(name = "idx_payment_user", columnList = "user_id"),
                @Index(name = "idx_payment_status", columnList = "payment_status"),
                @Index(name = "idx_payment_gateway", columnList = "gateway_name")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_payment_number",
                        columnNames = "payment_number"
                )
        }
)
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Example : PAY202600001
     */
    @NotBlank(message = "Payment number is required")
    @Column(name = "payment_number", nullable = false, length = 30, updatable = false)
    private String paymentNumber;

    /**
     * Order Service Reference
     */
    @NotNull(message = "Order Id is required")
    @Column(name = "order_id", nullable = false)
    private Long orderId;

    /**
     * User Service Reference
     */
    @NotNull(message = "User Id is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Builder.Default
    @Column(nullable = false, length = 10)
    private String currency = "INR";

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "gateway_name", nullable = false, length = 30)
    private GatewayName gatewayName = GatewayName.MOCK;

    @Column(name = "gateway_order_id", length = 100)
    private String gatewayOrderId;

    @Column(name = "gateway_payment_id", length = 100)
    private String gatewayPaymentId;

    @Column(name = "gateway_transaction_id", length = 100)
    private String gatewayTransactionId;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    /**
     * Payment attempt history
     */
    @OneToMany(
            mappedBy = "payment",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<PaymentTransaction> paymentTransactions = new HashSet<>();

    /**
     * Refund history
     */
    @OneToMany(
            mappedBy = "payment",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<Refund> refunds = new HashSet<>();
}