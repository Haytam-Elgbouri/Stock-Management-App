package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Partner;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class BonDeCommandeClientDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private Long clientId;
    private List<BCClientLineDTO> lines;
    private List<BonDeLivraisonDTO> bls;
    private Long prixTotalHT;
}
