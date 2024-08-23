package com.kyk.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCartItemRequest {
    private Long menuId;
    private int quantity;
}
