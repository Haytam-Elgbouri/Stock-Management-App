package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.ArticleColorPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleColorPriceRepository extends JpaRepository<ArticleColorPrice, Long> {
    Optional<ArticleColorPrice> findByArticleIdAndColorId(Long articleId, Long colorId);
}
