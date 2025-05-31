package com.scalux.stockManagement.services;

import com.scalux.stockManagement.dtos.ArticleDTO;

import java.util.List;

public interface IArticleService {
    ArticleDTO addArticle(ArticleDTO dto);
    List<ArticleDTO> getAll();
    ArticleDTO getById(Long id);
    ArticleDTO update(Long id, ArticleDTO dto);
    void delete(Long id);
}
