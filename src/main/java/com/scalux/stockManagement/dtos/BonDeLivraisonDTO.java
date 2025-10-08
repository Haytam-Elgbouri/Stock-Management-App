package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.BLLine;
import com.scalux.stockManagement.entities.BonDeCommande;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BonDeLivraisonDTO {
    private Long id;
    private String reference;
    private LocalDate date;
    private List<BLLineDTO> lines;
//    private BonDeCommandeDTO bc;
    private Long PrixTotalHT;
    private Boolean isValidated;

}
