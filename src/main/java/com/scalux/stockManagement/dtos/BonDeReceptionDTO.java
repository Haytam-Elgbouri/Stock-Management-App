package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BonDeReceptionDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private List<BRLineDTO> lines;
//    private BonDeCommandeDTO bc;
    private Long PrixTotalHT;
    private Boolean isValidated;

}
