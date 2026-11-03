//package com.scalux.stockManagement.seeders;
//
//import com.scalux.stockManagement.entities.Article;
//import com.scalux.stockManagement.entities.ArticleColorPrice;
//import com.scalux.stockManagement.entities.Color;
//import com.scalux.stockManagement.enums.Family;
//import com.scalux.stockManagement.repositories.ArticleColorPriceRepository;
//import com.scalux.stockManagement.repositories.ArticleRepository;
//import com.scalux.stockManagement.repositories.ColorRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//@Configuration
//@Order(2)
//public class ArticleSeeder {
//
//    @Bean
//    CommandLineRunner seedArticles(
//            ArticleRepository articleRepository,
//            ColorRepository colorRepository,
//            ArticleColorPriceRepository articleColorPriceRepository
//    ) {
//        return args -> {
//
//            if (articleRepository.count() == 0) {
//
//                Article article = new Article();
//                article.setReference("AR001");
//                article.setDesignation("article...");
//                article.setFamily(Family.BARRE); // assuming family 1 = BARRE
//                article.setType("ml");
//                article.setLongueur(6L);
//
//                Article savedArticle = articleRepository.save(article);
//
//                Color color1 = colorRepository.findById(1L)
//                        .orElseThrow(() -> new RuntimeException("Color 1 not found"));
//
//                Color color2 = colorRepository.findById(2L)
//                        .orElseThrow(() -> new RuntimeException("Color 2 not found"));
//
//                ArticleColorPrice price1 = new ArticleColorPrice();
//                price1.setArticle(savedArticle);
//                price1.setColor(color1);
//                price1.setPrixUnitaireHT(100L);
//                price1.setPrixTotalHT(100L * article.getLongueur());
//
//                ArticleColorPrice price2 = new ArticleColorPrice();
//                price2.setArticle(savedArticle);
//                price2.setColor(color2);
//                price2.setPrixUnitaireHT(120L);
//                price2.setPrixTotalHT(120L * article.getLongueur());
//
//                articleColorPriceRepository.save(price1);
//                articleColorPriceRepository.save(price2);
//
//                System.out.println("Article AR001 seeded");
//
//            }
//
//        };
//    }
//}