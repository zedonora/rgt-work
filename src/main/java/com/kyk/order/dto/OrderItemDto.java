package com.kyk.order.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderItemDto {
    private Long id;
    private Long menuId;
    private int quantity;
    private BigDecimal price;

    public OrderItemDto(Long id, Long menuId, int quantity, BigDecimal price) {
        this.id = id;
        this.menuId = menuId;
        this.quantity = quantity;
        this.price = price;
    }

}