package com.scalux.stockManagement.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BonDeReception {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    private LocalDate date;

    @OneToMany(mappedBy = "br", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BRLine> lines;

    @ManyToOne
    private BonDeCommande bc;

    private Long PrixTotalHT;

    private Boolean isValidated;
}
