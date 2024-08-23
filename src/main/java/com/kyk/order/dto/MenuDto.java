package com.kyk.order.dto;

import com.kyk.order.entity.Menu;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MenuDto {
    private Long id;
    private String name;
    private BigDecimal price;
    public MenuDto(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
    }
}
