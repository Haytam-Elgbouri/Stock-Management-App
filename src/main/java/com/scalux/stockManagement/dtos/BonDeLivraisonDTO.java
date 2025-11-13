package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BonDeLivraisonDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private List<BLLineDTO> lines;
    private Long PrixTotalHT;
    private Boolean isValidated;
}
