package com.scalux.stockManagement.entities;

import com.scalux.stockManagement.enums.Family;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;
    private String designation;

    @Enumerated(EnumType.STRING)
    private Family family; // ACCESSORY, BARRE, PIECE

    private String type; // e.g., "meter", "unit"
    private BigDecimal longueur;
    private BigDecimal prixUnitaireHT;
    private BigDecimal prixTotalHT;
}
