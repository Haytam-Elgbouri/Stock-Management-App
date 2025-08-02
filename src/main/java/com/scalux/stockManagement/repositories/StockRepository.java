package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByArticleIdAndColor(Long articleId, String color);
}
