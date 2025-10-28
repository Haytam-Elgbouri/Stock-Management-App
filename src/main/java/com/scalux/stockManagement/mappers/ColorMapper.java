package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.ColorDTO;
import com.scalux.stockManagement.entities.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface ColorMapper {
    Color toEntity(ColorDTO colorDTO);
    ColorDTO toDto(Color color);
}
