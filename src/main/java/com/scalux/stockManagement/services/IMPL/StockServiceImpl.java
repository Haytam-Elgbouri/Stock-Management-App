package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.dtos.StockTotalDTO;
import com.scalux.stockManagement.entities.ArticleColorPrice;
import com.scalux.stockManagement.entities.Stock;
import com.scalux.stockManagement.mappers.StockMapper;
import com.scalux.stockManagement.repositories.ArticleColorPriceRepository;
import com.scalux.stockManagement.repositories.StockRepository;
import com.scalux.stockManagement.services.IStockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements IStockService {
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ArticleColorPriceRepository articleColorPriceRepository;

    @Override
    public StockDTO addToStock(StockDTO dto) {
        Stock stock = stockMapper.toEntity(dto);
        Optional<ArticleColorPrice> articleColorPrice = articleColorPriceRepository.findByArticleIdAndColorId(stock.getArticle().getId(),stock.getColor().getId());
        ArticleColorPrice prixArticle = articleColorPrice
                .orElseThrow(() -> new RuntimeException("Article color prixArticle not found"));


        if (stockRepository.existsByArticleIdAndColorId(stock.getArticle().getId(),stock.getColor().getId())) {
            Optional<Stock> existingStock = stockRepository.findByArticleIdAndColorId(stock.getArticle().getId(),stock.getColor().getId());
            Long existingPrixLineStock = existingStock.get().getPrixLineStock();
            Long existingQuantity = existingStock.get().getQuantity();
            Long addedQuantity = stock.getQuantity();
            existingStock.get().setQuantity(existingQuantity + addedQuantity);
            existingStock.get().setPrixLineStock(existingPrixLineStock + (addedQuantity * prixArticle.getPrixTotalHT()));
            return stockMapper.toDto(stockRepository.save(existingStock.orElse(null)));
        }else {
            stock.setPrixArticleHT(prixArticle.getPrixTotalHT());
            stock.setPrixLineStock(prixArticle.getPrixTotalHT() * stock.getQuantity());
            return stockMapper.toDto(stockRepository.save(stock));
        }

//        stockRepository.findByArticleIdAndColorId(stock.getArticle().getId(),stock.getColor().getId()
//                )
//                .ifPresentOrElse(existingStock -> {
//                    Long existingPrixLineStock = existingStock.getPrixLineStock();
//                    Long existingQuantity = existingStock.getQuantity();
//                    Long addedQuantity = stock.getQuantity();
//                    existingStock.setQuantity(existingQuantity + addedQuantity);
//                    existingStock.setPrixLineStock(existingPrixLineStock + (addedQuantity * prixArticle.getPrixTotalHT()));
//                    stockRepository.save(existingStock);
//                }, () -> {
//                    stock.setPrixArticleHT(prixArticle.getPrixTotalHT());
//                    stock.setPrixLineStock(prixArticle.getPrixTotalHT() * stock.getQuantity());
//                    stockMapper.toDto(stockRepository.save(stock));
//                });

//        stock.setPrixArticleHT(prixArticle.getPrixTotalHT());
//        stock.setPrixLineStock(prixArticle.getPrixTotalHT() * stock.getQuantity());
//        return stockMapper.toDto(stockRepository.save(stock));
    }


    @Override
    public List<StockDTO> getAll() {
        return stockRepository.findAll().stream()
                .map(stockMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StockTotalDTO getTotalStock() {
        StockTotalDTO stockTotalDTO = new StockTotalDTO();
        List<StockDTO> stockLines = stockRepository.findAll().stream()
                .map(stockMapper::toDto)
                .toList();
        long valeurStock = 0L;
        for (StockDTO stockDTO : stockLines) {
            valeurStock += stockDTO.getPrixLineStock();
        }
        stockTotalDTO.setStockLines(stockLines);
        stockTotalDTO.setValuerStock(valeurStock);
        return stockTotalDTO;
    }

    @Override
    public StockDTO updateStock(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setQuantity(quantity);
        stock.setPrixLineStock(stock.getPrixArticleHT() * quantity);
        return stockMapper.toDto(stockRepository.save(stock));
    }


}
