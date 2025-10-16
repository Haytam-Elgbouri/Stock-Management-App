package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BRLineDTO;
import com.scalux.stockManagement.entities.BRLine;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface BRLineMapper {

    BRLine toEntity(BRLineDTO BRLineDTO);

    BRLineDTO toDto(BRLine BRLine);
}
