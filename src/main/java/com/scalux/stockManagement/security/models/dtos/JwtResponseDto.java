package com.scalux.stockManagement.security.models.dtos;


import com.scalux.stockManagement.security.models.enums.Role;

public record JwtResponseDto(
        String token,
        Long id,
        String username,
        Role role
) {
}
