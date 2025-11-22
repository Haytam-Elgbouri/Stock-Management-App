package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.*;
import com.scalux.stockManagement.repositories.*;
import com.scalux.stockManagement.services.IBonDeLivraisonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonDeLivraisonServiceImpl implements IBonDeLivraisonService {

    private final BonDeLivraisonRepository blRepository;
    private final BonDeCommandeClientRepository bccRepository;
    private final BCClientLineRepository bcClientLineRepository;
    private final BLLineRepository blLineRepository;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final BonDeCommandeClientMapper bccMapper;
    private final BonDeLivraisonMapper blMapper;
    private final ArticleMapper articleMapper;
    private final ColorMapper colorMapper;


    @Override
    public BonDeLivraisonDTO addBl(CreateBRDTO createBRDTO, Long id) {
        boolean hasUnvalidatedBL = blRepository.existsByBcClientIdAndIsValidatedFalse(id);

        if (hasUnvalidatedBL) {
            throw new RuntimeException("Cannot create a new BL until the previous BL is validated.");
        }

        BonDeLivraison bl = new BonDeLivraison();
        BonDeCommandeClient bcc = bccRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de commande not found"));
        bl.setBcClient(bcc);
        LocalDate localDate = LocalDate.now();
        bl.setDate(localDate);
        bl.setReference(createBRDTO.getReference());
        List<BLLine> lines = new ArrayList<>();
        Long prixTotal = 0L;
        BonDeCommandeClientDTO bccDto = bccMapper.toDto(bcc);

        for (BCClientLineDTO bcClientLineDTO : bccDto.getLines()){


            BLLine line = new BLLine();
            line.setArticle(articleMapper.toEntity(bcClientLineDTO.getArticle()));
            line.setColor(colorMapper.toEntity(bcClientLineDTO.getColor()));
            line.setQuantity(bcClientLineDTO.getQuantity());
            line.setRemainingBefore(bcClientLineDTO.getRemaining());
            line.setPrixArticleHT(bcClientLineDTO.getPrixArticleHT());

            line.setDelivered(0L);
            BCClientLine bcClientLine = bcClientLineRepository.findById(bcClientLineDTO.getId()).orElseThrow();
            line.setBcClientLine(bcClientLine);
            line.setBl(bl);

            lines.add(line);

        }

        bl.setLines(lines);
        bl.setPrixTotalHT(prixTotal);
        bl.setIsValidated(false);

        BonDeLivraison saved = blRepository.save(bl);

        return blMapper.toDto(saved);

    }

    @Override
    public List<BonDeLivraisonDTO> getAll() {
        return blRepository.findAll().stream()
                .map(blMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonDeLivraisonDTO getById(Long id) {
        return blMapper.toDto(blRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BC Client not found")));
    }

    @Override
    @Transactional
    public void deliverBL(BLDeliverDTO blDeliverDTO) {

        BonDeLivraison bl = blRepository.findById(blDeliverDTO.getBlId())
                .orElseThrow(() -> new RuntimeException("BL not found"));

        if (Boolean.TRUE.equals(bl.getIsValidated())) {
            throw new RuntimeException("BL Already validated");
        }

        for (BLLineDeliverDTO lineDTO : blDeliverDTO.getLines()) {

            BLLine line = blLineRepository.findById(lineDTO.getId())
                    .orElseThrow(() -> new RuntimeException("BLLine not found: " + lineDTO.getId()));

            Long deliveredQty = lineDTO.getDeliveredQuantity();

            // Check that we're not delivering more than remaining
            if (deliveredQty > line.getRemainingBefore()) {
                throw new RuntimeException(
                        "Delivered quantity exceeds remaining for BLLine: " + line.getId()
                );
            }

            Stock stockLine = stockRepository
                    .findByArticleIdAndColorId(line.getArticle().getId(), line.getColor().getId())
                    .orElseThrow(() -> new RuntimeException("This article with this color does not exist in stock"));

            if (stockLine.getQuantity() < deliveredQty) {
                throw new RuntimeException("Insufficient stock for this delivery");
            }


            // Apply changes
            line.setDelivered(line.getDelivered() + deliveredQty);
            line.setRemainingAfter(line.getRemainingBefore() - deliveredQty);

            blLineRepository.save(line);
        }
    }


//    @Override
//    public void validate(Long id) {
//        BonDeLivraison bonDeLivraison = blRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Bon de Livraison not found"));
//
//
//        bonDeLivraison.setIsValidated(true);
//        blRepository.save(bonDeLivraison);
//
//        for (BLLine blLine : bonDeLivraison.getLines()) {
//            if (blLine.getDelivered() != 0) {
//                Stock stockLine = stockRepository.findByArticleIdAndColorId(blLine.getArticle().getId(), blLine.getColor().getId()).orElseThrow(() -> new RuntimeException("This article with this color doesnt exist in the stock"));
//
//                if (stockLine.getQuantity() >= blLine.getDelivered()){
//                    Long existingPrixLineStock = stockLine.getPrixLineStock();
//                    Long existingQuantity = stockLine.getQuantity();
//                    Long deliveredQuantity = blLine.getDelivered();
//                    stockLine.setQuantity(existingQuantity - deliveredQuantity);
//                    stockLine.setPrixLineStock(existingPrixLineStock - (deliveredQuantity * blLine.getPrixArticleHT()));
//                    if (stockLine.getQuantity()==0){
//                        stockRepository.delete(stockLine);
//                    }else {
//                        stockRepository.save(stockLine);
//                    }
//
//                }else {
//                    throw new RuntimeException("Stock insuffisent");
//                }
//
//
//                BCClientLine bcClientLine = blLine.getBcClientLine();
//                if (bcClientLine != null) {
//                    bcClientLine.setDelivered(bcClientLine.getDelivered() + blLine.getDelivered());
//                    bcClientLine.setRemaining(bcClientLine.getRemaining() - blLine.getDelivered());
//                    bcClientLineRepository.save(bcClientLine);
//                }
//            }
//        }
//    }

    private void updateStockForLine(BLLine blLine) {

        Stock stockLine = stockRepository
                .findByArticleIdAndColorId(blLine.getArticle().getId(), blLine.getColor().getId())
                .orElseThrow(() ->
                        new RuntimeException("This article with this color doesn't exist in stock")
                );

        Long existingQty = stockLine.getQuantity();
        Long deliveredQty = blLine.getDelivered();

        if (existingQty < deliveredQty) {
            throw new RuntimeException("Stock insuffisant");
        }

        stockLine.setQuantity(existingQty - deliveredQty);
        stockLine.setPrixLineStock(
                stockLine.getPrixLineStock() - (deliveredQty * blLine.getPrixArticleHT())
        );

        if (stockLine.getQuantity() == 0) {
            stockRepository.delete(stockLine);
        } else {
            stockRepository.save(stockLine);
        }
    }


    private void updateBCClientLine(BLLine blLine) {

        BCClientLine bcClientLine = blLine.getBcClientLine();
        if (bcClientLine == null) return;

        Long delivered = blLine.getDelivered();

        bcClientLine.setDelivered(bcClientLine.getDelivered() + delivered);
        bcClientLine.setRemaining(bcClientLine.getRemaining() - delivered);

        bcClientLineRepository.save(bcClientLine);
    }


    @Override
    @Transactional
    public void validate(Long id) {

        BonDeLivraison bl = blRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de Livraison not found"));

        bl.setIsValidated(true);
        blRepository.save(bl);

        for (BLLine blLine : bl.getLines()) {
            if (blLine.getDelivered() == 0) continue;

            updateStockForLine(blLine);
            updateBCClientLine(blLine);
        }
    }

    @Override
    public void delete(Long id) {
        blRepository.deleteById(id);
    }

}
