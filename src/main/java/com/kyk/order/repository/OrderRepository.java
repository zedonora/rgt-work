package com.kyk.order.repository;

import com.kyk.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    List<Order> findByTableRestaurantOwnerId(Long ownerId);
    List<Order> findByTableRestaurantId(Long restaurantId);
}