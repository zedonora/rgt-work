package com.kyk.order.dto;

import com.kyk.order.entity.DiningTable;
import com.kyk.order.entity.Menu;
import com.kyk.order.entity.User;
import lombok.Builder;

import java.util.List;

@Builder
public class RestaurantDto {
    private Long id;
    private String name;
    private User owner;
    private List<DiningTable> tables;
    private List<Menu> menus;
}
