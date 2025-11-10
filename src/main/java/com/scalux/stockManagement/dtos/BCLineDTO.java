package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Color;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BCLineDTO {
    private Long id;
    private ArticleDTO article;
    private ColorDTO color;
    @NotNull
    private Long quantity;

    private Long prixArticleHT;

    @NotNull
    private Long remaining;
    @NotNull
    private Long received;
    private Long prixTotalLigne;
}
