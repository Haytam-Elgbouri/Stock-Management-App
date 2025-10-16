package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BCLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;
    @NotNull
    private Long quantity;
    @NotNull
    private Long remaining;
    @NotNull
    private Long received;

    @ManyToOne
    private Article article;

    @ManyToOne
    private BonDeCommande bc;

    private Long prixTotalLigne; // calculated = article.prixUnitaireHT * quantity
}
