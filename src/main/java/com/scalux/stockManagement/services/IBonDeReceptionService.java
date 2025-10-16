package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.*;

import java.util.List;

public interface IBonDeReceptionService {
    BonDeReceptionDTO addBR(CreateBRDTO createBRDTO, Long id);
    List<BonDeReceptionDTO> getAll();
    BonDeReceptionDTO getById(Long id);
//    void deliver(RecieveDTO recieveDTO);

    void receiveBR(BRRecieveDTO brRecieveDTO);

    void validate(Long id);
}
