package com.fva.menuservice.entity;

import com.fva.menuservice.enums.FoodType;
import com.fva.menuservice.enums.SpicyLevel;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "menu_items",
        indexes = {
                @Index(name = "idx_menu_item_restaurant", columnList = "restaurant_id"),
                @Index(name = "idx_menu_item_category", columnList = "category_id"),
                @Index(name = "idx_menu_item_name", columnList = "name"),
                @Index(name = "idx_menu_item_food_type", columnList = "food_type"),
                @Index(name = "idx_menu_item_available", columnList = "available")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_menu_item_code",
                        columnNames = "item_code"
                )
        }
)
public class MenuItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique Menu Item Code.
     * Example : ITEM10001
     */
    @NotBlank(message = "Item code is required")
    @Column(name = "item_code", nullable = false, length = 30, updatable = false)
    private String itemCode;

    /**
     * Restaurant Service Reference.
     */
    @NotNull(message = "Restaurant Id is required")
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    /**
     * Category Reference.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_menu_item_category")
    )
    private MenuCategory menuCategory;

    @NotBlank(message = "Item name is required")
    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(name = "short_description", length = 255)
    private String shortDescription;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.00")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Builder.Default
    @DecimalMin(value = "0.00")
    @Column(name = "discount_percentage", precision = 5, scale = 2)
    private BigDecimal discountPercentage = BigDecimal.ZERO;

    @Builder.Default
    @DecimalMin(value = "0.00")
    @Column(name = "discounted_price", precision = 10, scale = 2)
    private BigDecimal discountedPrice = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "food_type", nullable = false, length = 20)
    private FoodType foodType;

    @Enumerated(EnumType.STRING)
    @Column(name = "spicy_level", length = 20)
    private SpicyLevel spicyLevel;

    @PositiveOrZero
    @Column(name = "preparation_time")
    private Integer preparationTime;

    @PositiveOrZero
    private Integer calories;

    @Builder.Default
    @Column(nullable = false)
    private Boolean available = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean recommended = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean bestseller = false;

    @Builder.Default
    @Column(nullable = false)
    private Boolean customizable = false;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;
}