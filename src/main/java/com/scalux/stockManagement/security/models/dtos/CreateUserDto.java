package com.scalux.stockManagement.security.models.dtos;


import com.scalux.stockManagement.security.models.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
        @NotBlank String username,
//        @NotBlank String lastName,
//
//        @NotBlank String email,
//        @NotBlank String phone,
//        @NotBlank String CIN,
        @NotNull Role role,
        Boolean isActive,
        @NotNull String password
) {
}