package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BonDeReceptionDTO;
import com.scalux.stockManagement.entities.BonDeReception;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BonDeReceptionMapper {

    BonDeReception toEntity(BonDeReceptionDTO bonDeReceptionDTO);

    BonDeReceptionDTO toDto(BonDeReception bonDeReception);
}
