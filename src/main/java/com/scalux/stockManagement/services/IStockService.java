package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.dtos.StockTotalDTO;

import java.util.List;

public interface IStockService {
    StockDTO addToStock(StockDTO dto);
    List<StockDTO> getAll();
    StockTotalDTO getTotalStock();
}
