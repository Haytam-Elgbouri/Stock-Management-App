package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.dtos.ArticleDTO;
import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.services.IStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4300")
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {
    private final IStockService stockService;

    @PostMapping
    public ResponseEntity<StockDTO>add(@RequestBody @Valid StockDTO dto){
        return ResponseEntity.ok(stockService.addToStock(dto));
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAll() {
        return ResponseEntity.ok(stockService.getAll());
    }

}
