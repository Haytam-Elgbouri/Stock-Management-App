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
public class BonDeCommandeClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate date;

    private String client;

    @OneToMany(mappedBy = "bcClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BCClientLine> lines;

//    BL:
//    @OneToMany(mappedBy = "bc", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BonDeReception> brs;

    private Long prixTotalHT;

}
