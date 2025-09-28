package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.entities.BonDeLivraison;

public interface BonDeLivraisonMapper {

    BonDeLivraison toEntity(BonDeLivraisonDTO bonDeLivraisonDTO);

    BonDeLivraisonDTO toDto(BonDeLivraison bonDeLivraison);
}
