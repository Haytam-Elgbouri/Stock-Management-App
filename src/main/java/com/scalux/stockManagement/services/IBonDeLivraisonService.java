package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;

import java.util.List;

public interface IBonDeLivraisonService {
    BonDeLivraisonDTO addBL(BonDeLivraisonDTO bonDeLivraisonDTO, Long id);
    List<BonDeLivraisonDTO> getAll();
    BonDeLivraisonDTO getById(Long id);
}
