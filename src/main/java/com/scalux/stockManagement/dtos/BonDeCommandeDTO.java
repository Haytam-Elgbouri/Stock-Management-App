package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BonDeCommandeDTO {
    private Long id;
    private LocalDate date;
    private List<BCLineDTO> lignes;
    private BigDecimal prixTotalHT;
}

