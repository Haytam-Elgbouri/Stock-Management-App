package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BLLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String color;

    private Long quantity;

    private Long remainingBefore;

    private Long delivered;

    private Long remainingAfter;

    @ManyToOne
    private Article article;

    @ManyToOne
    private BonDeLivraison bl;

    @ManyToOne
    private BCLine bcLine;

    private Long prixTotalLigne;
}
