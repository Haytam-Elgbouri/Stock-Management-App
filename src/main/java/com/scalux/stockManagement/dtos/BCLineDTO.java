package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import lombok.Data;

@Data
public class BCLineDTO {
    private ArticleDTO article;
    private String color;
    private Integer quantity;
}
