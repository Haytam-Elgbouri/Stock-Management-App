package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.Stock;
import com.scalux.stockManagement.mappers.StockMapper;
import com.scalux.stockManagement.repositories.StockRepository;
import com.scalux.stockManagement.services.IStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StockServiceImpl implements IStockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public StockDTO addToStock(StockDTO dto) {
        Stock stock = stockMapper.toEntity(dto);
        return stockMapper.toDto(stockRepository.save(stock));
    }


    @Override
    public List<StockDTO> getAll() {
        return stockRepository.findAll().stream()
                .map(stockMapper::toDto)
                .collect(Collectors.toList());
    }
}
