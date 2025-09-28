package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BLLineDTO;
import com.scalux.stockManagement.entities.BLLine;

public interface BLLineMapper {

    BLLine toEntity(BLLineDTO blLineDTO);

    BLLineDTO toDto(BLLine blLine);
}
