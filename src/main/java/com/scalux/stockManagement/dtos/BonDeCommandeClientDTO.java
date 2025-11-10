package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BonDeCommandeClientDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private String client;
    private List<BCClientLineDTO> lines;
//    private List<BonDeReceptionDTO> brs;
    private Long prixTotalHT;
}
