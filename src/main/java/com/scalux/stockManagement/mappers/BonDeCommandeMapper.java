package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.entities.BonDeCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { BCLineMapper.class })
public interface BonDeCommandeMapper {
    BonDeCommandeDTO toDto(BonDeCommande entity);
    BonDeCommande toEntity(BonDeCommandeDTO dto);
}
