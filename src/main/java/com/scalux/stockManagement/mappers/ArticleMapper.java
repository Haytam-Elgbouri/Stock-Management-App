package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.entities.Article;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticleDTO toDto(Article entity);
    Article toEntity(ArticleDTO dto);
}
