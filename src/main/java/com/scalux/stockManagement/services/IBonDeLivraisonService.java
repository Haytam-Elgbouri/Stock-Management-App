package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.BLDeliverDTO;
import com.scalux.stockManagement.dtos.BRRecieveDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.dtos.CreateBRDTO;

import java.util.List;

public interface IBonDeLivraisonService {
    BonDeLivraisonDTO addBl(CreateBRDTO createBRDTO, Long id);
    List<BonDeLivraisonDTO> getAll();
    BonDeLivraisonDTO getById(Long id);
    void deliverBL(BLDeliverDTO blDeliverDTO);

    void validate(Long id);
}
