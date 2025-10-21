package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.ColorPriceDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.ArticleColorPrice;
import com.scalux.stockManagement.entities.Color;
import com.scalux.stockManagement.enums.Family;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.repositories.ArticleColorPriceRepository;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.repositories.ColorRepository;
import com.scalux.stockManagement.services.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ColorRepository colorRepository;
    private final ArticleColorPriceRepository articleColorPriceRepository;

    @Override
    public ArticleDTO addArticle(ArticleDTO dto) {
        Article article = articleMapper.toEntity(dto);
        Family family = article.getFamily();

        // Initialize colorPrices
        article.setColorPrices(new ArrayList<>());

        // Save article
        Article savedArticle = articleRepository.save(article);

        if (dto.getColorPrices() != null) {
            for (ColorPriceDTO colorPriceDTO : dto.getColorPrices()) {
                ArticleColorPrice articleColorPrice = new ArticleColorPrice();
                articleColorPrice.setArticle(savedArticle);

                Color color = colorRepository.findById(colorPriceDTO.getColorId())
                        .orElseThrow(() -> new RuntimeException("Color not found"));
                articleColorPrice.setColor(color);

                Long prixUnitaire = colorPriceDTO.getPrixUnitaireHT();
                Long prixTotal = (family == Family.BARRE && article.getLongueur() != null)
                        ? prixUnitaire * article.getLongueur()
                        : colorPriceDTO.getPrixTotalHT();

                articleColorPrice.setPrixUnitaireHT(prixUnitaire);
                articleColorPrice.setPrixTotalHT(prixTotal);

                // Save color price
                ArticleColorPrice savedColorPrice = articleColorPriceRepository.save(articleColorPrice);

                // Add to savedArticle
                savedArticle.getColorPrices().add(savedColorPrice);
            }
        }

        // ✅ Map only savedArticle -> DTO
        return articleMapper.toDto(savedArticle);
    }



    @Override
    public List<ArticleDTO> getAll() {
        return articleRepository.findAll().stream()
                .map(articleMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArticleDTO getById(Long id){
        Article article = articleRepository.findById(id).orElseThrow(()->new RuntimeException());
        return articleMapper.toDto(article);
    }

    @Override
    public ArticleDTO update(Long id, ArticleDTO dto) {
        Article existing = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        existing.setReference(dto.getReference());
        existing.setDesignation(dto.getDesignation());
        existing.setFamily(dto.getFamily());
        existing.setType(dto.getType());

        return articleMapper.toDto(articleRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
