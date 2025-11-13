package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BonDeLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate date;

    @OneToMany(mappedBy = "bl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BLLine> lines;

    @ManyToOne
    private BonDeCommandeClient bcClient;

    private Long PrixTotalHT;

    private Boolean isValidated;
}
