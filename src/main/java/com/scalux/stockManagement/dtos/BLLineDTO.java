package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BonDeCommande;
import com.scalux.stockManagement.entities.BonDeLivraison;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

public class BLLineDTO {
    private Long id;
    private String color;
    private Long quantity;
    private Long remainingBefore;
    private Long delivered;
    private Long remainingAfter;
    private Article article;
    private BonDeLivraison bl;
    private Long prixTotalLigne;
}
