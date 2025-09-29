package com.scalux.stockManagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BCLineDTO {
    private Long id;
    private ArticleDTO article;
    private String color;
    @NotNull
    private Long quantity;
    @NotNull
    private Long remaining;
    @NotNull
    private Long delivered;
    private Long prixTotalLigne;
}
