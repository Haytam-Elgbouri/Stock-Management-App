package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class ColorPriceDTO {
    private Long colorId;
    private String colorName; // <-- add this
    private Long prixUnitaireHT;
    private Long prixTotalHT;
}
