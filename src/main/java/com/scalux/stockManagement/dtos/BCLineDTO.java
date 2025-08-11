package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import lombok.Data;

@Data
public class BCLineDTO {
    private Long articleId;
    private String color;
    private Integer quantity;
}
