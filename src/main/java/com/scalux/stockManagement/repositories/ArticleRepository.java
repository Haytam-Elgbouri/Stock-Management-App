package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}
