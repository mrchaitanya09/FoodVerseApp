package com.fva.orderservice.entity;

import com.fva.orderservice.enums.OrderStatus;
import com.fva.orderservice.enums.PaymentMode;
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
        name = "orders",
        indexes = {
                @Index(name = "idx_order_number", columnList = "order_number"),
                @Index(name = "idx_order_user", columnList = "user_id"),
                @Index(name = "idx_order_restaurant", columnList = "restaurant_id"),
                @Index(name = "idx_order_status", columnList = "order_status"),
                @Index(name = "idx_order_date", columnList = "ordered_at")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_order_number",
                        columnNames = "order_number"
                )
        }
)
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Example : ORD202600001
     */
    @NotBlank
    @Column(name = "order_number", nullable = false, length = 30, updatable = false)
    private String orderNumber;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @Column(name = "delivery_partner_id")
    private Long deliveryPartnerId;

    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "coupon_id")
    private Long couponId;

    @NotNull
    @Column(name = "delivery_address_id", nullable = false)
    private Long deliveryAddressId;

    @NotBlank
    @Column(name = "customer_name", nullable = false, length = 150)
    private String customerName;

    @NotBlank
    @Column(name = "customer_phone", nullable = false, length = 20)
    private String customerPhone;

    @NotBlank
    @Column(name = "restaurant_name", nullable = false, length = 150)
    private String restaurantName;

    @DecimalMin("0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Builder.Default
    @DecimalMin("0.00")
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Builder.Default
    @DecimalMin("0.00")
    @Column(name = "delivery_charge", precision = 10, scale = 2)
    private BigDecimal deliveryCharge = BigDecimal.ZERO;

    @Builder.Default
    @DecimalMin("0.00")
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @DecimalMin("0.00")
    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode", nullable = false, length = 20)
    private PaymentMode paymentMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 30)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Column(name = "order_notes", length = 500)
    private String orderNotes;

    @Column(name = "estimated_delivery_time")
    private LocalDateTime estimatedDeliveryTime;

    @Builder.Default
    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt = LocalDateTime.now();

    private LocalDateTime confirmedAt;

    private LocalDateTime preparedAt;

    private LocalDateTime pickedUpAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", length = 255)
    private String cancellationReason;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<OrderItem> orderItems = new HashSet<>();
}