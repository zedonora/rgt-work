package com.kyk.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tables", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "table_number"})})
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @OneToMany(mappedBy = "table")
    private List<Order> orders;

    // Getters and setters
}
