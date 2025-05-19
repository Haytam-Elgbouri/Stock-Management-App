package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.entities.BCLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BCLineMapper {
    BCLineDTO toDto(BCLine entity);
    BCLine toEntity(BCLineDTO dto);
}
