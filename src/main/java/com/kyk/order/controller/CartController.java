package com.kyk.order.controller;

import com.kyk.order.dto.AddToCartRequest;
import com.kyk.order.dto.UpdateCartItemRequest;
import com.kyk.order.entity.CartItem;
import com.kyk.order.security.CustomUserDetails;
import com.kyk.order.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequest request, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUserId();
            CartItem cartItem = cartService.addToCart(userId, request.getMenuId(), request.getQuantity());
            return ResponseEntity.ok(cartItem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("장바구니에 추가하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(@RequestBody UpdateCartItemRequest request, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUserId();
            CartItem updatedItem = cartService.updateCartItem(userId, request.getMenuId(), request.getQuantity());
            return ResponseEntity.ok(updatedItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("장바구니 항목 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
