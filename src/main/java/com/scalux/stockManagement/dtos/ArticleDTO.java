package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.enums.Family;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ArticleDTO {
    private Long id;
    private String reference;
    private String designation;
    private Family family;
    private String type;
    private BigDecimal prixUnitaireHT;
}

