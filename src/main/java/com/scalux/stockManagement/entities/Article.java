package com.scalux.stockManagement.entities;

import com.scalux.stockManagement.enums.Family;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

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
    private Long longueur;


    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<ArticleColorPrice> colorPrices;
}
