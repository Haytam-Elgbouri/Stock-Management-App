package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BCLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    private Integer quantity;
    private Integer remaining;
    private Integer delivered;

    @ManyToOne
    private Article article;

    @ManyToOne
    private BonDeCommande bc;

    private BigDecimal prixTotalLigne; // calculated = article.prixUnitaireHT * quantity
}
