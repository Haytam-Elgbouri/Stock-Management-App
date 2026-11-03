package com.scalux.stockManagement.security.models.dtos;


import com.scalux.stockManagement.security.models.enums.Role;

public record ResponseUserDto(

        String username,
        Role role
) {
}
