package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BonDeCommandeClientDTO;
import com.scalux.stockManagement.entities.BonDeCommandeClient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BonDeCommandeClientMapper {

    BonDeCommandeClient toEntity(BonDeCommandeClientDTO bonDeCommandeClientDTO);

    BonDeCommandeClientDTO toDto(BonDeCommandeClient bonDeCommandeClient);
}
