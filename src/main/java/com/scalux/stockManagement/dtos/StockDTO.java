package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class StockDTO {
    private Long id;
    private Long articleId;
    private String color;
    private int quantity;
}
