package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import lombok.Data;

@Data
public class StockDTO {
    private Long id;
    private Article article;
    private String color;
    private Long quantity;
}
