package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BRLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Color color;

    private Long quantity;

    private Long remainingBefore;

    private Long received;

    private Long remainingAfter;

    @ManyToOne
    private Article article;

    private Long prixArticleHT;

    @ManyToOne
    private BonDeReception br;

    @ManyToOne
    private BCLine bcLine;

    private Long prixTotalLigne;
}
