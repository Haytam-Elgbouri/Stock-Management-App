package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BonDeCommandeDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private String supplierReference;
    private List<BCLineDTO> lignes;
    private BigDecimal prixTotalHT;
//    private boolean isValidated;

}

