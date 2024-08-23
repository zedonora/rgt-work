package com.kyk.order.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    private String role;

    @OneToMany(mappedBy = "owner")
    private List<Restaurant> ownedRestaurants;

    @OneToMany(mappedBy = "user")
    private List<CartItem> cartItems;

    // Getters and setters
}