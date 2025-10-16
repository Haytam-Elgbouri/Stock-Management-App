package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;

import java.util.List;

public interface IBonDeCommandeService {
    BonDeCommandeDTO addBC(BonDeCommandeDTO dto);
    List<BonDeCommandeDTO> getAll();
    BonDeCommandeDTO getById(Long id);
    BonDeCommandeDTO update(Long id, BonDeCommandeDTO dto);
//    BonDeCommandeDTO validateBC(Long id);
    void delete(Long id);
//    void deliver(DeliverDTO deliverDTO);
}
