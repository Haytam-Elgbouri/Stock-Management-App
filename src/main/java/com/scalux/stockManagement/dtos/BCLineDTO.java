package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class BCLineDTO {
    private Long article;
    private String color;
    private Integer quantity;
}
