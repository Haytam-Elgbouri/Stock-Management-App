package com.scalux.stockManagement.mappers;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.dtos.ColorPriceDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.ArticleColorPrice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "colorPrices", source = "colorPrices")
    ArticleDTO toDto(Article article);

    Article toEntity(ArticleDTO dto);

    @Mapping(target = "colorId", source = "color.id")
    @Mapping(target = "colorName", source = "color.name")
    @Mapping(target = "prixUnitaireHT", source = "prixUnitaireHT")
    @Mapping(target = "prixTotalHT", source = "prixTotalHT")
    ColorPriceDTO toDto(ArticleColorPrice colorPrice);
}


