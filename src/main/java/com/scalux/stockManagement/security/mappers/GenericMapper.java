package com.scalux.stockManagement.security.mappers;

public interface GenericMapper<ENTITY, DTO> {
    ENTITY toEntity(DTO dto);
    DTO toDto(ENTITY entity);
}
