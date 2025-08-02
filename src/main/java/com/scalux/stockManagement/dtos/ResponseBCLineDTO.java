package com.scalux.stockManagement.dtos;

import com.scalux.stockManagement.entities.Article;
import lombok.Data;

@Data
public class ResponseBCLineDTO {
    private Article article;
    private String color;
    private Integer quantity;
}
