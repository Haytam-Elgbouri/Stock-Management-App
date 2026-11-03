package com.scalux.stockManagement.security.models.dtos;

public record LoginRequestDto(
        String username,
        String password
) {
}
