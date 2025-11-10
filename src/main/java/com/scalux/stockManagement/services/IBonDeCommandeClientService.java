package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.BonDeCommandeClientDTO;

import java.util.List;

public interface IBonDeCommandeClientService {
    BonDeCommandeClientDTO addBcc(BonDeCommandeClientDTO dto);
    List<BonDeCommandeClientDTO> getAll();
    BonDeCommandeClientDTO getById(Long id);
}
