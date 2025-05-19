package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.entities.BonDeCommande;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonDeCommandeMapper {
    BonDeCommandeDTO toDto(BonDeCommande entity);
    BonDeCommande toEntity(BonDeCommandeDTO dto);
}
