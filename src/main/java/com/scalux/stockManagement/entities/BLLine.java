package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BLLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Color color;

    private Long quantity;

    private Long remainingBefore;

    private Long delivered;

    private Long remainingAfter;

    @ManyToOne
    private Article article;

    private Long prixArticleHT;

    @ManyToOne
    private BonDeLivraison bl;

    @ManyToOne
    private BCClientLine bcClientLine;

    private Long prixTotalLigne;

}
