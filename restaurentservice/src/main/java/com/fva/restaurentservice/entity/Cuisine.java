package com.fva.restaurentservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
        name = "cuisines",
        indexes = {
                @Index(name = "idx_cuisine_name", columnList = "name")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_cuisine_name", columnNames = "name")
        }
)
public class Cuisine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Cuisine name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "icon_url", length = 500)
    private String iconUrl;

    @PositiveOrZero
    @Builder.Default
    @Column(name = "display_order")
    private Integer displayOrder = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;

    @ManyToMany(mappedBy = "cuisines", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<Restaurant> restaurants = new HashSet<>();
}