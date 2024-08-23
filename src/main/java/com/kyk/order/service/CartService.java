package com.kyk.order.service;

import com.kyk.order.entity.CartItem;
import com.kyk.order.entity.Menu;
import com.kyk.order.entity.User;
import com.kyk.order.repository.CartItemRepository;
import com.kyk.order.repository.MenuRepository;
import com.kyk.order.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository, MenuRepository menuRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public CartItem addToCart(Long userId, Long menuId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. menuId=" + menuId));

        CartItem cartItem = cartItemRepository.findByUserAndMenu(user, menu)
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    return existingItem;
                })
                .orElseGet(() -> CartItem.builder()
                        .user(user)
                        .menu(menu)
                        .quantity(quantity)
                        .build());

        return cartItemRepository.save(cartItem);
    }

    @Transactional
    public CartItem updateCartItem(Long userId, Long menuId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다. menuId=" + menuId));

        CartItem cartItem = cartItemRepository.findByUserAndMenu(user, menu)
                .orElseThrow(() -> new IllegalArgumentException("장바구니에 해당 메뉴가 존재하지 않습니다."));

        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }
}