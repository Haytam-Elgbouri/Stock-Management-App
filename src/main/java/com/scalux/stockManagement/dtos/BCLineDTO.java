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
    private Integer quantity;
    @NotNull
    private Integer remaining;
    @NotNull
    private Integer delivered;
    private BigDecimal prixTotalLigne;
}
