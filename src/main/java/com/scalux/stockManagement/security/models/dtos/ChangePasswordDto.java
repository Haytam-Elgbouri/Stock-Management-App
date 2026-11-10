package com.scalux.stockManagement.security.models.dtos;

import jakarta.validation.constraints.NotNull;

public record ChangePasswordDto(
        @NotNull String oldPassword,
        @NotNull String newPassword
) {
}