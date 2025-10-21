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

    private final BonDeCommandeMapper bcMapper;
    private final StockMapper stockMapper;

    @Override
    public BonDeCommandeDTO addBC(BonDeCommandeDTO dto) {
        BonDeCommande bc = new BonDeCommande();
        bc.setReference(dto.getReference());
        LocalDate localDate = LocalDate.now();
        bc.setDate(localDate);
//        bc.setValidated(dto.isValidated());
        bc.setSupplierReference(dto.getSupplierReference());

        List<BCLine> lines = new ArrayList<>();
        Long prixTotal = 0L;

        for (BCLineDTO lineDTO : dto.getLines()) {
            Long articleId = lineDTO.getArticle() != null ? lineDTO.getArticle().getId() : null;
            if (articleId == null) {
                throw new RuntimeException("Article ID is required");
            }

            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setRemaining(lineDTO.getQuantity());
            line.setReceived(0L);
//            line.setColor(lineDTO.getColor());
            line.setBc(bc);

            Long lineTotal = line.getQuantity();
            line.setPrixTotalLigne(lineTotal);

            lines.add(line);
            prixTotal = prixTotal + lineTotal;
        }

        bc.setLines(lines);
        bc.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(bc);

        // Update or create stock
//        for (BCLine line : saved.getLignes()) {
//            stockRepository.findByArticleIdAndColor(line.getArticle().getId(), line.getColor())
//                    .ifPresentOrElse(existingStock -> {
//                        existingStock.setQuantity(existingStock.getQuantity() + line.getQuantity());
//                        stockRepository.save(existingStock);
//                    }, () -> {
//                        Stock stock = new Stock();
//                        stock.setArticle(line.getArticle());
//                        stock.setColor(line.getColor());
//                        stock.setQuantity(line.getQuantity());
//                        stockRepository.save(stock);
//                    });
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
        bcLineRepository.deleteAll(existing.getLines());
        existing.getLines().clear();

        existing.setDate(dto.getDate());
//        existing.setValidated(dto.isValidated());

        Long prixTotal = 0L;
        List<BCLine> lignes = new ArrayList<>();

        for (BCLineDTO lineDTO : dto.getLines()) {
            Article article = articleRepository.findById(lineDTO.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
//            line.setColor(lineDTO.getColor());
            line.setBc(existing);

//            BigDecimal lineTotal = article.getPrixUnitaireHT().multiply(BigDecimal.valueOf(line.getQuantity()));
            Long lineTotal = line.getQuantity();
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal + lineTotal;
        }

        existing.setLines(lignes);
        existing.setPrixTotalHT(prixTotal);

        BonDeCommande saved = bcRepository.save(existing);

        // Optional: You might want to clear old stock lines here if you're updating a previously validated BC

//        if (saved.isValidated()) {
            for (BCLine line : saved.getLines()) {
                StockDTO stockDTO = new StockDTO();
                stockDTO.setArticle(line.getArticle());
//                stockDTO.setColor(line.getColor());
                stockDTO.setQuantity(line.getQuantity());

                Stock stock = stockMapper.toEntity(stockDTO);
                stock.setArticle(line.getArticle());
                stockRepository.save(stock);
            }
//        }

        return bcMapper.toDto(saved);
    }

//    @Override
//    public BonDeCommandeDTO validateBC(Long id) {
//        BonDeCommande bc = bcRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("BC not found"));
//
//        if (bc.isValidated()) {
//            throw new RuntimeException("BC already validated");
//        }
//
//        bc.setValidated(true);
//        BonDeCommande saved = bcRepository.save(bc);
//
//        for (BCLine line : saved.getLignes()) {
//            StockDTO stockDTO = new StockDTO();
//            stockDTO.setArticle(line.getArticle());
//            stockDTO.setColor(line.getColor());
//            stockDTO.setQuantity(line.getQuantity());
//
//            Stock stock = stockMapper.toEntity(stockDTO);
//            stock.setArticle(line.getArticle());
//            stockRepository.save(stock);
//        }
//
//        return bcMapper.toDto(saved);
//    }


    @Override
    public void delete(Long id) {
        bcRepository.deleteById(id);
    }

//    @Override
//    public void deliver(DeliverDTO deliverDTO) {
//
//        BCLine bcLine = bcLineRepository.findById(deliverDTO.getId()).orElse(null);
////        if (deliverDTO.getDeliveredQuantity() <= bcLine.getRemaining()) {
//            bcLine.setDelivered(deliverDTO.getDeliveredQuantity() + bcLine.getDelivered());
//            bcLine.setRemaining(bcLine.getRemaining() - deliverDTO.getDeliveredQuantity());
//            bcLineRepository.save(bcLine);
//
//            stockRepository.findByArticleIdAndColor(bcLine.getArticle().getId(), bcLine.getColor())
//                    .ifPresentOrElse(existingStock -> {
//                       existingStock.setQuantity(existingStock.getQuantity() + deliverDTO.getDeliveredQuantity());
//                       stockRepository.save(existingStock);
//                    },() -> {StockDTO stockDTO = new StockDTO();
//                            stockDTO.setColor(bcLine.getColor());
//                            stockDTO.setQuantity(deliverDTO.getDeliveredQuantity());
//                            stockDTO.setArticle(bcLine.getArticle());
//                            stockRepository.save(stockMapper.toEntity(stockDTO));
//                    });
//        }
//    }
////}
}