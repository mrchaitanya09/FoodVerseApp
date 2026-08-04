package com.fva.menuservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "menu_categories",
        indexes = {
                @Index(name = "idx_menu_category_restaurant", columnList = "restaurant_id"),
                @Index(name = "idx_menu_category_name", columnList = "name"),
                @Index(name = "idx_menu_category_active", columnList = "active")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_menu_category_code",
                        columnNames = "category_code"
                )
        }
)
public class MenuCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Unique category code.
     * Example: CAT10001
     */
    @NotBlank(message = "Category code is required")
    @Column(name = "category_code", nullable = false, length = 30, updatable = false)
    private String categoryCode;

    /**
     * Restaurant Service Reference.
     */
    @NotNull(message = "Restaurant Id is required")
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;

    @NotBlank(message = "Category name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @PositiveOrZero
    @Builder.Default
    @Column(name = "display_order", nullable = false)
    private Integer displayOrder = 0;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @Builder.Default
    @Column(nullable = false)
    private Boolean deleted = false;

    /**
     * One Category can have multiple Menu Items.
     */
    @OneToMany(
            mappedBy = "menuCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private Set<MenuItem> menuItems = new HashSet<>();
}