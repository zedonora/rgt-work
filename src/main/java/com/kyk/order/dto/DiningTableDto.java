package com.kyk.order.dto;

import com.kyk.order.entity.Order;
import com.kyk.order.entity.Restaurant;
import lombok.Builder;
import java.util.List;

@Builder
public class DiningTableDto {
    private Long id;
    private Restaurant restaurant;
    private Integer tableNumber;
    private List<Order> orders;
}
