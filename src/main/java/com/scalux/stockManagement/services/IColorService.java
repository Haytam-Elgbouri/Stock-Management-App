package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.ColorDTO;

import java.util.List;

public interface IColorService {
    ColorDTO addColor(ColorDTO colorDTO);
    List<ColorDTO> getAllColors();
    void deleteColor(Long id);
}
