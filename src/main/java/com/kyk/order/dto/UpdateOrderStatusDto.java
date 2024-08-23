package com.kyk.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateOrderStatusDto {
    private String status;

    // 기본 생성자
    public UpdateOrderStatusDto() {}

    // 생성자
    public UpdateOrderStatusDto(String status) {
        this.status = status;
    }
}