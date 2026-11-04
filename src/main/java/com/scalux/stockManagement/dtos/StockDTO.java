package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class StockDTO {
    private Long id;
    private ArticleDTO article;
    private Long prixArticleHT;
    private ColorDTO color;
    private Long quantity;
    private Long prixLineStock;
}
