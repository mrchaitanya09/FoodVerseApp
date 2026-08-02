package com.fva.restaurentservice.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.fva.restaurentservice.enums.ApprovalStatus;
import com.fva.restaurentservice.enums.RestaurantStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "restaurants",
        indexes = {
                @Index(name = "idx_restaurant_name", columnList = "name"),
                @Index(name = "idx_restaurant_city", columnList = "city"),
                @Index(name = "idx_restaurant_status", columnList = "restaurant_status"),
                @Index(name = "idx_restaurant_owner", columnList = "owner_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_restaurant_code", columnNames = "restaurant_code"),
                @UniqueConstraint(name = "uk_restaurant_email", columnNames = "email")
        }
)
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "restaurant_code", nullable = false, length = 30, updatable = false)
    private String restaurantCode;

    @NotNull
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "display_name", length = 150)
    private String displayName;

    @Column(length = 1000)
    private String description;

    @Email
    @Column(nullable = false, length = 120)
    private String email;

    @Pattern(regexp = "^[0-9]{10,15}$")
    @Column(nullable = false, length = 15)
    private String phone;

    @NotBlank
    @Column(name = "address_line1", nullable = false)
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String city;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String state;

    @NotBlank
    @Column(nullable = false, length = 80)
    private String country;

    @NotBlank
    @Column(name = "postal_code", nullable = false, length = 10)
    private String postalCode;

    @Column(precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Builder.Default
    @Column(name = "delivery_available", nullable = false)
    private Boolean deliveryAvailable = true;

    @Builder.Default
    @Column(name = "takeaway_available", nullable = false)
    private Boolean takeawayAvailable = true;

    @Builder.Default
    @Column(name = "dine_in_available", nullable = false)
    private Boolean dineInAvailable = false;

    @PositiveOrZero
    @Column(name = "minimum_order_amount", precision = 10, scale = 2)
    private BigDecimal minimumOrderAmount;

    @PositiveOrZero
    @Column(name = "delivery_charge", precision = 10, scale = 2)
    private BigDecimal deliveryCharge;

    @PositiveOrZero
    @Column(name = "estimated_delivery_time")
    private Integer estimatedDeliveryTime;

    @DecimalMin("0.0")
    @Column(name = "average_rating", precision = 2, scale = 1)
    private BigDecimal averageRating;

    @Builder.Default
    @PositiveOrZero
    @Column(name = "total_ratings")
    private Integer totalRatings = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", nullable = false, length = 30)
    @Builder.Default
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "restaurant_status", nullable = false, length = 30)
    @Builder.Default
    private RestaurantStatus restaurantStatus = RestaurantStatus.CLOSED;

    @Column(name = "gst_number", length = 30)
    private String gstNumber;

    @Column(name = "fssai_license_number", length = 50)
    private String fssaiLicenseNumber;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verified = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "restaurant_cuisines",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id")
    )
    @Builder.Default
    private Set<Cuisine> cuisines = new HashSet<>();

    @OneToMany(
            mappedBy = "restaurant",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<RestaurantImage> restaurantImages = new HashSet<>();
}