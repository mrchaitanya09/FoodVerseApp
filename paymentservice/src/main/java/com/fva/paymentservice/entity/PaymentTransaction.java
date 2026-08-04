package com.fva.paymentservice.entity;

import com.fva.paymentservice.enums.PaymentStatus;
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
        name = "payment_transactions",
        indexes = {
                @Index(name = "idx_transaction_payment", columnList = "payment_id"),
                @Index(name = "idx_transaction_number", columnList = "transaction_number"),
                @Index(name = "idx_gateway_transaction", columnList = "gateway_transaction_id"),
                @Index(name = "idx_transaction_status", columnList = "payment_status")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_transaction_number",
                        columnNames = "transaction_number"
                )
        }
)
public class PaymentTransaction extends BaseEntity {

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
            foreignKey = @ForeignKey(name = "fk_payment_transaction_payment")
    )
    private Payment payment;

    /**
     * Internal Transaction Number
     * Example: TXN202600001
     */
    @NotBlank(message = "Transaction number is required")
    @Column(name = "transaction_number",
            nullable = false,
            length = 30,
            updatable = false)
    private String transactionNumber;

    /**
     * Gateway Transaction Id
     */
    @Column(name = "gateway_transaction_id", length = 100)
    private String gatewayTransactionId;

    @NotNull(message = "Amount is required")
    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    private PaymentStatus paymentStatus;

    /**
     * JSON request sent to payment gateway.
     */
    @Lob
    @Column(name = "request_payload", columnDefinition = "LONGTEXT")
    private String requestPayload;

    /**
     * JSON response received from payment gateway.
     */
    @Lob
    @Column(name = "response_payload", columnDefinition = "LONGTEXT")
    private String responsePayload;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}