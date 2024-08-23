package com.kyk.order.service;

import com.kyk.order.dto.*;
import com.kyk.order.entity.*;
import com.kyk.order.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderService.class);

    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final DiningTableRepository diningTableRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public OrderService(UserRepository userRepository, CartItemRepository cartItemRepository,
                        OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        DiningTableRepository diningTableRepository, RestaurantRepository restaurantRepository) {
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.diningTableRepository = diningTableRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getCustomerOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse createOrder(Long userId, OrderRequest orderRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        DiningTable table = diningTableRepository.findById(orderRequest.getTableId())
                .orElseThrow(() -> new RuntimeException("테이블을 찾을 수 없습니다."));

        List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        if (cartItems.isEmpty()) {
            throw new RuntimeException("장바구니가 비어 있습니다.");
        }

        Order order = Order.builder().table(table).status("CONFIRM").user(user).build();
        orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menu(cartItem.getMenu())
                    .quantity(cartItem.getQuantity())
                    .price(cartItem.getMenu().getPrice())
                    .build();
            return orderItem;
        }).collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        cartItemRepository.deleteAll(cartItems);

        List<OrderItemDto> orderItemDtos = orderItems.stream()
                .map(item -> new OrderItemDto(
                        item.getId(),
                        item.getMenu().getId(),
                        item.getQuantity(),
                        item.getPrice()))
                .collect(Collectors.toList());

        // OrderResponse 생성자에 맞게 수정
        return new OrderResponse(order.getId(), order.getStatus(), orderItemDtos);
    }

    @Transactional
    public Order createOrderFromCart(Long userId, Long tableId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. userId=" + userId));

        DiningTable table = diningTableRepository.findById(tableId)
                .orElseThrow(() -> new IllegalArgumentException("테이블을 찾을 수 없습니다. tableId=" + tableId));

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어있습니다.");
        }

        Order order = Order.builder()
                .table(table)
                .status("CONFIRM")
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Menu menu = cartItem.getMenu();
            if (menu == null) {
                throw new IllegalStateException("장바구니 아이템에 연결된 메뉴가 없습니다. cartItemId=" + cartItem.getId());
            }
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(cartItem.getQuantity())
                    .price(menu.getPrice())
                    .build();
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        try {
            Order savedOrder = orderRepository.save(order);
            cartItemRepository.deleteByUserId(userId);
            return savedOrder;
        } catch (Exception e) {
            log.error("주문 저장 중 오류 발생", e);
            throw new RuntimeException("주문 생성 중 오류가 발생했습니다.", e);
        }
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getUserOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        List<Order> orders = orderRepository.findByTableRestaurantOwnerId(user.getId());
        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getRestaurantOrders(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("식당을 찾을 수 없습니다."));

        List<Order> orders = orderRepository.findByTableRestaurantId(restaurantId);
        return orders.stream()
                .map(this::convertToOrderDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderDto updateOrderStatus(Long orderId, UpdateOrderStatusDto updateDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        order.setStatus(updateDto.getStatus());
        Order updatedOrder = orderRepository.save(order);
        return convertToOrderDto(updatedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDto getOrderDetail(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

        List<OrderItemDto> orderItems = orderItemRepository.findByOrderId(orderId).stream()
                .map(item -> new OrderItemDto(item.getId(), item.getMenu().getId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());

        return new OrderDto(order.getId(), order.getTable().getId(), order.getStatus(),
                order.getCreatedAt(), orderItems);
    }

    private OrderDto convertToOrderDto(Order order) {
        List<OrderItemDto> orderItems = order.getOrderItems().stream()
                .map(item -> new OrderItemDto(item.getId(), item.getMenu().getId(), item.getQuantity(), item.getPrice()))
                .collect(Collectors.toList());

        return new OrderDto(order.getId(), order.getTable().getId(), order.getStatus(), order.getCreatedAt(), orderItems);
    }
}