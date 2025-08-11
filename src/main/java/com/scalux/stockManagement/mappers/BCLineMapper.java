package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BCLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BCLineMapper {

    @Mapping(target = "article", source = "articleId")
    BCLine toEntity(BCLineDTO dto);

    @Mapping(target = "articleId", source = "article.id")
    BCLineDTO toDto(BCLine entity);

    // Helper method: MapStruct uses this to convert articleId to Article
    default Article map(Long articleId) {
        if (articleId == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleId);
        return article;
    }

    // Optional: for reverse mapping Article → articleId
    default Long map(Article article) {
        return article == null ? null : article.getId();
    }
}
