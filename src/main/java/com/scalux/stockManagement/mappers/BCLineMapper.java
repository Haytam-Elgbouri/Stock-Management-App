package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BCLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { ArticleMapper.class })
public interface BCLineMapper {

    // Map full ArticleDTO -> Article entity
    @Mapping(target = "bc", ignore = true) // prevent infinite recursion
    BCLine toEntity(BCLineDTO dto);

    // Map full Article entity -> ArticleDTO
    BCLineDTO toDto(BCLine entity);
}

