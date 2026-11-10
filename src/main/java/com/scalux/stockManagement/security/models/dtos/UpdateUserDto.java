package com.scalux.stockManagement.security.models.dtos;

import com.scalux.stockManagement.security.models.enums.Role;

public record UpdateUserDto(
        String username,
//        String lastName,
//        String email,
//        String phone,
//        String CIN,
        Role role
) {
}