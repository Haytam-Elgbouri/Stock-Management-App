package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BCLine;
import com.scalux.stockManagement.entities.BonDeCommande;
import com.scalux.stockManagement.entities.Stock;
import com.scalux.stockManagement.mappers.BonDeCommandeMapper;
import com.scalux.stockManagement.mappers.StockMapper;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.repositories.BCLineRepository;
import com.scalux.stockManagement.repositories.BonDeCommandeRepository;
import com.scalux.stockManagement.repositories.StockRepository;
import com.scalux.stockManagement.services.IBonDeCommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonDeCommandeServiceImpl implements IBonDeCommandeService {

    private final BonDeCommandeRepository bcRepository;
    private final ArticleRepository articleRepository;
    private final BCLineRepository bcLineRepository;
    private final StockRepository stockRepository;

    private final BonDeCommandeMapper bcMapper;
    private final StockMapper stockMapper;

    @Override
    public BonDeCommandeDTO addBC(BonDeCommandeDTO dto) {
        BonDeCommande bc = new BonDeCommande();
        bc.setReference(dto.getReference());
        bc.setDate(dto.getDate());
        bc.setValidated(dto.isValidated());
        bc.setSupplierReference(dto.getSupplierReference());
        List<BCLine> lignes = new ArrayList<>();
        BigDecimal prixTotal = BigDecimal.ZERO;

        for (BCLineDTO lineDTO : dto.getLignes()) {
            Article article = articleRepository.findById(lineDTO.getArticle())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setColor(lineDTO.getColor());
            line.setBc(bc);

            BigDecimal lineTotal = article.getPrixUnitaireHT().multiply(BigDecimal.valueOf(line.getQuantity()));
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal.add(lineTotal);
        }

        bc.setLignes(lignes);
        bc.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(bc);

//        if (saved.isValidated()) {
            System.out.println("********************************");
        for (BCLine line : saved.getLignes()) {
            Optional<Stock> existingStockOpt = stockRepository
                    .findByArticleIdAndColor(line.getArticle().getId(), line.getColor());

            if (existingStockOpt.isPresent()) {
                Stock existingStock = existingStockOpt.get();
                int updatedQuantity = existingStock.getQuantity() + line.getQuantity();
                existingStock.setQuantity(updatedQuantity);
                stockRepository.save(existingStock);
            } else {
                Stock stock = new Stock();
                stock.setArticle(line.getArticle());
                stock.setColor(line.getColor());
                stock.setQuantity(line.getQuantity());
                stockRepository.save(stock);
            }
        }

//        }

        return bcMapper.toDto(saved);
    }

    @Override
    public List<BonDeCommandeDTO> getAll() {
        return bcRepository.findAll().stream()
                .map(bcMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonDeCommandeDTO getById(Long id) {
        return bcMapper.toDto(bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BC not found")));
    }

    @Override
    public BonDeCommandeDTO update(Long id, BonDeCommandeDTO dto) {
        BonDeCommande existing = bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BC not found"));

        // Clear old lines
        bcLineRepository.deleteAll(existing.getLignes());
        existing.getLignes().clear();

        existing.setDate(dto.getDate());
        existing.setValidated(dto.isValidated());

        BigDecimal prixTotal = BigDecimal.ZERO;
        List<BCLine> lignes = new ArrayList<>();

        for (BCLineDTO lineDTO : dto.getLignes()) {
            Article article = articleRepository.findById(lineDTO.getArticle())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setColor(lineDTO.getColor());
            line.setBc(existing);

            BigDecimal lineTotal = article.getPrixUnitaireHT().multiply(BigDecimal.valueOf(line.getQuantity()));
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal.add(lineTotal);
        }

        existing.setLignes(lignes);
        existing.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(existing);

        // Optional: You might want to clear old stock lines here if you're updating a previously validated BC

        if (saved.isValidated()) {
            for (BCLine line : saved.getLignes()) {
                StockDTO stockDTO = new StockDTO();
                stockDTO.setArticle(line.getArticle());
                stockDTO.setColor(line.getColor());
                stockDTO.setQuantity(line.getQuantity());

                Stock stock = stockMapper.toEntity(stockDTO);
                stock.setArticle(line.getArticle());
                stockRepository.save(stock);
            }
        }

        return bcMapper.toDto(saved);
    }

    @Override
    public BonDeCommandeDTO validateBC(Long id) {
        BonDeCommande bc = bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BC not found"));

        if (bc.isValidated()) {
            throw new RuntimeException("BC already validated");
        }

        bc.setValidated(true);
        BonDeCommande saved = bcRepository.save(bc);

        for (BCLine line : saved.getLignes()) {
            StockDTO stockDTO = new StockDTO();
            stockDTO.setArticle(line.getArticle());
            stockDTO.setColor(line.getColor());
            stockDTO.setQuantity(line.getQuantity());

            Stock stock = stockMapper.toEntity(stockDTO);
            stock.setArticle(line.getArticle());
            stockRepository.save(stock);
        }

        return bcMapper.toDto(saved);
    }


    @Override
    public void delete(Long id) {
        bcRepository.deleteById(id);
    }
}
