package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockMapper {

//    @Mapping(source = "article.id", target = "articleId")
    StockDTO toDto(Stock stock);

//    @Mapping(source = "articleId", target = "article.id")
    Stock toEntity(StockDTO dto);
}
