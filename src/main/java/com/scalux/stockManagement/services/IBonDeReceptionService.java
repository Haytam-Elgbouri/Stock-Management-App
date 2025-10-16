package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.*;

import java.util.List;

public interface IBonDeReceptionService {
    BonDeReceptionDTO addBR(CreateBRDTO createBRDTO, Long id);
    List<BonDeReceptionDTO> getAll();
    BonDeReceptionDTO getById(Long id);
//    void deliver(RecieveDTO recieveDTO);

    void deliverBR(BRRecieveDTO brRecieveDTO);

    void validate(Long id);
}
