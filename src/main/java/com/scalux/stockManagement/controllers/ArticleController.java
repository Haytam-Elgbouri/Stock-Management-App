package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.services.IArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final IArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleDTO> add(@RequestBody @Valid ArticleDTO dto) {
        return ResponseEntity.ok(articleService.addArticle(dto));
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getById( @PathVariable Long id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @PutMapping("/{id}")
    public ArticleDTO update(@PathVariable Long id, @RequestBody ArticleDTO dto) {
        return articleService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        articleService.delete(id);
    }
}
