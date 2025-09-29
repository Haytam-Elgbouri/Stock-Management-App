package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.enums.Family;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.services.IArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements IArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDTO addArticle(ArticleDTO dto) {
        Article article = articleMapper.toEntity(dto);

        Family family = article.getFamily();
        Long prixUnitaire = article.getPrixUnitaireHT();
        Long prixTotal = 0L;

        if (family == Family.ACCESSORY || family == Family.JOINT) {
            prixTotal = prixUnitaire;
        } else if (family == Family.BARRE) {
            // assuming getLongueur() returns BigDecimal
            if (article.getLongueur() != null) {
                prixTotal = prixUnitaire * article.getLongueur();
            } else {
                // handle null length if needed
                prixTotal = 0L;

            }
        }

        article.setPrixTotalHT(prixTotal);

        Article saved = articleRepository.save(article);
        return articleMapper.toDto(saved);
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
        existing.setPrixUnitaireHT(dto.getPrixUnitaireHT());

        return articleMapper.toDto(articleRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
