package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.ArticleColorPrice;
import com.scalux.stockManagement.enums.Family;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ArticleDTO {
    private Long id;
    private String reference;
    private String designation;
    private Family family;
    private String type;
    private Long longueur;
    private List<ColorPriceDTO> colorPrices;
}

