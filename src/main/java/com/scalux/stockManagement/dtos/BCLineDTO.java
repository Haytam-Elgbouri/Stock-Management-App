package com.scalux.stockManagement.dtos;

import lombok.Data;

@Data
public class BCLineDTO {
    private Long articleId;
    private String color;
    private String supplierReference;
    private Integer quantity;
}
