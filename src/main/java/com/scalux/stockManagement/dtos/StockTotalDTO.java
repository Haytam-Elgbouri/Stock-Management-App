package com.scalux.stockManagement.dtos;

import lombok.Data;

import java.util.List;

@Data
public class StockTotalDTO {
    private List<StockDTO> stockLines;
    private Long valuerStock;
}
