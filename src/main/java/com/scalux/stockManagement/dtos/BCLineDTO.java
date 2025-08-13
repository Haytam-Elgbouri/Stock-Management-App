package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BCLineDTO {
    private ArticleDTO article;
    private String color;
    private Integer quantity;
    private BigDecimal prixTotalLigne;
}
