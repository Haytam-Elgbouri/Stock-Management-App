package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BCClientLineDTO;
import com.scalux.stockManagement.entities.BCClientLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BCClientLineMapper {

    BCClientLine toEntity(BCClientLineDTO bcClientLineDTO);

    BCClientLineDTO toDto(BCClientLine bcClientLine);
}
