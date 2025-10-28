package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.ColorDTO;
import com.scalux.stockManagement.entities.Color;
import com.scalux.stockManagement.mappers.ColorMapper;
import com.scalux.stockManagement.repositories.ColorRepository;
import com.scalux.stockManagement.services.IColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ColorServiceImpl implements IColorService {

    private final ColorMapper colorMapper;
    private final ColorRepository colorRepository;

    @Override
    public ColorDTO addColor(ColorDTO colorDTO) {
        Color color = colorMapper.toEntity(colorDTO);
        return colorMapper.toDto(colorRepository.save(color));
    }

    @Override
    public List<ColorDTO> getAllColors() {
        return colorRepository.findAll().stream()
                .map(colorMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteColor(Long id) {
        colorRepository.deleteById(id);
    }
}
