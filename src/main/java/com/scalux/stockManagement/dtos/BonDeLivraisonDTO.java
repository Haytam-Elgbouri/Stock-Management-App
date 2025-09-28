package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.BLLine;
import com.scalux.stockManagement.entities.BonDeCommande;
import jakarta.persistence.ManyToOne;

import java.util.List;

public class BonDeLivraisonDTO {
    private Long id;
    private String reference;
    private List<BLLine> lines;
    private BonDeCommande bc;
    private Long PrixTotalHT;
}
