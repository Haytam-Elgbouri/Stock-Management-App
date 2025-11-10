package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BonDeCommandeClient;
import com.scalux.stockManagement.entities.Color;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BCClientLineDTO {

    private Long id;
    private ArticleDTO article;
    private ColorDTO color;
    private Long quantity;
    private Long delivered;
    private Long remaining;
    private Long prixTotalLigne;

}
