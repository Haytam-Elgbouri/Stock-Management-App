package com.scalux.stockManagement.security.models.dtos;


import com.scalux.stockManagement.security.models.enums.Role;

public record UserDto(

        Long id,
        String username,
        Role role,
        Boolean isActive
) {
}
