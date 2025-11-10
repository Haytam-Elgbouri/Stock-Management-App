package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.dtos.StockDTO;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.mappers.BonDeCommandeMapper;
import com.scalux.stockManagement.mappers.StockMapper;
import com.scalux.stockManagement.repositories.*;
import com.scalux.stockManagement.services.IBonDeCommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonDeCommandeServiceImpl implements IBonDeCommandeService {

    private final BonDeCommandeRepository bcRepository;
    private final ArticleRepository articleRepository;
    private final BCLineRepository bcLineRepository;
    private final StockRepository stockRepository;
    private final ArticleColorPriceRepository articleColorPriceRepository;

    private final BonDeCommandeMapper bcMapper;
    private final StockMapper stockMapper;
    private final ArticleMapper articleMapper;

    @Override
    public BonDeCommandeDTO addBC(BonDeCommandeDTO dto) {
        BonDeCommande bc = new BonDeCommande();
        bc.setReference(dto.getReference());
        bc.setDate(LocalDate.now());
        bc.setSupplierReference(dto.getSupplierReference());

        List<BCLine> lines = new ArrayList<>();
        Long prixTotal = 0L;

        for (BCLineDTO lineDTO : dto.getLines()) {
            if (lineDTO.getArticle() == null || lineDTO.getArticle().getId() == null) {
                throw new RuntimeException("Article ID is required");
            }

            Article article = articleRepository.findById(lineDTO.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setRemaining(lineDTO.getQuantity());
            line.setReceived(0L);
            line.setBc(bc);

            if (lineDTO.getColor().getId() != null) {
                ArticleColorPrice colorPrice = articleColorPriceRepository
                        .findByArticleIdAndColorId(article.getId(), lineDTO.getColor().getId())
                        .orElseThrow(() -> new RuntimeException("Color price not found"));

                line.setColor(colorPrice.getColor());

                Long lineTotal = colorPrice.getPrixTotalHT() * line.getQuantity();
                line.setPrixTotalLigne(lineTotal);
                line.setPrixArticleHT(colorPrice.getPrixTotalHT());

                prixTotal += lineTotal;
            } else {
                throw new RuntimeException("Color is required for each BC line");
            }

            lines.add(line);
        }

        bc.setLines(lines);
        bc.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(bc);

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

        bcLineRepository.deleteAll(existing.getLines());
        existing.getLines().clear();

        existing.setDate(dto.getDate());
        Long prixTotal = 0L;
        List<BCLine> lignes = new ArrayList<>();

        for (BCLineDTO lineDTO : dto.getLines()) {
            Article article = articleRepository.findById(lineDTO.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setBc(existing);

            Long lineTotal = line.getQuantity();
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal + lineTotal;
        }

        existing.setLines(lignes);
        existing.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(existing);

            for (BCLine line : saved.getLines()) {
                StockDTO stockDTO = new StockDTO();
                stockDTO.setArticle(articleMapper.toDto(line.getArticle()));
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