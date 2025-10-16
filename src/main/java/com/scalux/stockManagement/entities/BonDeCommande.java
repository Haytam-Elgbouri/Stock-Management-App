package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonDeCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate date;
    private String supplierReference;

    @OneToMany(mappedBy = "bc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BCLine> lines;

    @OneToMany(mappedBy = "bc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BonDeReception> brs;

    private Long prixTotalHT;

//    private boolean isValidated;
}

