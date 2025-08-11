package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BCLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BCLineMapper {

    // Map articleId -> Article using the helper method
    @Mapping(target = "article", source = "articleId")
    BCLine toEntity(BCLineDTO dto);

    // Map Article -> articleId using the helper method
    @Mapping(target = "articleId", source = "article")
    BCLineDTO toDto(BCLine entity);

    // Long -> Article
    default Article map(Long articleId) {
        if (articleId == null) {
            return null;
        }
        Article article = new Article();
        article.setId(articleId);
        return article;
    }

    // Article -> Long
    default Long map(Article article) {
        return (article != null) ? article.getId() : null;
    }
}
