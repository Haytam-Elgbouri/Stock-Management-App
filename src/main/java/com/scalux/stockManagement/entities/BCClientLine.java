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
public class BCClientLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Color color;
    @NotNull
    private Long quantity;

    private Long prixArticleHT;

    @NotNull
    private Long delivered;

    @NotNull
    private Long remaining;

    @ManyToOne
    private Article article;

    @ManyToOne
    private BonDeCommandeClient bcClient;

    private Long prixTotalLigne;
}
