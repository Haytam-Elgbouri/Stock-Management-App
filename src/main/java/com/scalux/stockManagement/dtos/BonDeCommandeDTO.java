package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BonDeCommandeDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private String supplierReference;
    private List<BCLineDTO> lines;
    private List<BonDeReceptionDTO> brs;
    private Long prixTotalHT;
//    private boolean isValidated;

}

