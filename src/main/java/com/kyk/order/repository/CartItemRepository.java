package com.kyk.order.repository;

import com.kyk.order.entity.CartItem;
import com.kyk.order.entity.Menu;
import com.kyk.order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserAndMenu(User user, Menu menu);
    List<CartItem> findByUserId(Long userId);

    void deleteByUserId(Long userId);
}