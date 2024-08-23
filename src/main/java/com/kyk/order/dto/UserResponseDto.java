package com.kyk.order.dto;

import lombok.Builder;

@Builder
public class UserResponseDto {
    private String username;

    public UserResponseDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
