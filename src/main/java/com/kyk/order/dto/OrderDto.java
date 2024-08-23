package com.kyk.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {
    private Long id;
    private Long tableId;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemDto> orderItems;

    public OrderDto(Long id, Long tableId, String status, LocalDateTime createdAt, List<OrderItemDto> orderItems) {
        this.id = id;
        this.tableId = tableId;
        this.status = status;
        this.createdAt = createdAt;
        this.orderItems = orderItems;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getTableId() {
        return tableId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }
}