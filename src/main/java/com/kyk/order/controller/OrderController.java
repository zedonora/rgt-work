package com.kyk.order.controller;

import com.kyk.order.dto.*;
import com.kyk.order.entity.Order;
import com.kyk.order.security.CustomUserDetails;
import com.kyk.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        OrderResponse orderResponse = orderService.createOrder(userId, orderRequest);
        return ResponseEntity.ok(orderResponse);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getUserOrders(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getUserId();
        return ResponseEntity.ok(orderService.getCustomerOrders(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam Long tableId, Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUserId();
            Order createdOrder = orderService.createOrderFromCart(userId, tableId);
            return ResponseEntity.ok(createdOrder);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 생성 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<OrderDto>> getRestaurantOrders(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(orderService.getRestaurantOrders(restaurantId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusDto updateDto) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, updateDto));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderDetail(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderDetail(orderId));
    }
}