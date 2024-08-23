package com.kyk.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {
    private Long menuId;
    private int quantity;
}
