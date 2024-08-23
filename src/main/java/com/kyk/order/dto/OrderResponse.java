package com.kyk.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String status;
    private List<OrderItemDto> items;

    public OrderResponse(Long id, String status, List<OrderItemDto> items) {
        this.id = id;
        this.status = status;
        this.items = items;
    }
}