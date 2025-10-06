package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.dtos.CreateBLDTO;

import java.util.List;

public interface IBonDeLivraisonService {
    BonDeLivraisonDTO addBL(CreateBLDTO createBLDTO, Long id);
    List<BonDeLivraisonDTO> getAll();
    BonDeLivraisonDTO getById(Long id);
    //    void deliver(DeliverDTO deliverDTO);

}
