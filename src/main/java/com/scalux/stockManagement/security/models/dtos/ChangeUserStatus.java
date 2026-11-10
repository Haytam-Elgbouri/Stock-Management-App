package com.scalux.stockManagement.security.models.dtos;


import jakarta.validation.constraints.NotNull;

public record ChangeUserStatus(
        @NotNull Boolean isActive
) {
}