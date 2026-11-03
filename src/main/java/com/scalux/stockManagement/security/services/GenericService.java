package com.scalux.stockManagement.security.services;

import org.springframework.data.domain.Page;

import java.util.List;

public interface GenericService<CREATE_DTO, UPDATE_DTO, DTO, ID> {
    DTO create(CREATE_DTO dto);
    DTO update(UPDATE_DTO dto, Long id);
    Page<DTO> findAll(int pageNumber, int size);
    List<DTO> findAll();
    DTO findById(ID id);
    void delete(ID id);
}
