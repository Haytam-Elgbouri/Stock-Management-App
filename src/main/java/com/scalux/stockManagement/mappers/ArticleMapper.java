package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.entities.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = ".", source = ".")
    ArticleDTO toDto(Article entity);
    @Mapping(target = ".", source = ".")
    Article toEntity(ArticleDTO dto);
}
