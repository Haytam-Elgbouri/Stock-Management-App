package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class BRLineDTO {
    private Long id;
    private ColorDTO color;
    private Long quantity;
    private Long remainingBefore;
    private Long received;
    private Long remainingAfter;
    private ArticleDTO article;
//    private BonDeLivraison bl;
    private Long prixTotalLigne;
}
