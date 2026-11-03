package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class BLLineDTO {
    private Long id;
    private ColorDTO color;
    private Long quantity;
    private Long remainingBefore;
    private Long delivered;
    private Long remainingAfter;
    private ArticleDTO article;
    private Long prixArticleHT;
    private Long prixTotalLigne;
}
