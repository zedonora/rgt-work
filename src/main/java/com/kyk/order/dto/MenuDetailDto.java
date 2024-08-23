package com.kyk.order.dto;

import com.kyk.order.entity.Menu;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuDetailDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;

    public MenuDetailDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
    }

    // Getters
}