package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.*;
import com.scalux.stockManagement.repositories.*;
import com.scalux.stockManagement.services.IBonDeLivraisonService;
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
    public void deliverBL(BLDeliverDTO blDeliverDTO) {

        BonDeLivraison bl = blRepository.findById(blDeliverDTO.getBlId())
                .orElseThrow(() -> new RuntimeException("BR not found"));

        if (Boolean.TRUE.equals(bl.getIsValidated())) {
            throw new RuntimeException("BR Already validated");
        }

        for (BLLineDeliverDTO lineDTO : blDeliverDTO.getLines()) {
            BLLine line = blLineRepository.findById(lineDTO.getId())
                    .orElseThrow(() -> new RuntimeException("BRLine not found: " + lineDTO.getId()));

            line.setDelivered(lineDTO.getDeliveredQuantity() + line.getDelivered());
            line.setRemainingAfter(line.getRemainingBefore() - lineDTO.getDeliveredQuantity());

            blLineRepository.save(line);
        }
    }

    @Override
    public void validate(Long id) {
        BonDeLivraison bonDeLivraison = blRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de Livraison not found"));


        bonDeLivraison.setIsValidated(true);
        blRepository.save(bonDeLivraison);

        for (BLLine blLine : bonDeLivraison.getLines()) {
            if (blLine.getDelivered() != 0) {
                Stock stockLine = stockRepository.findByArticleIdAndColorId(blLine.getArticle().getId(), blLine.getColor().getId()).orElseThrow(null);

                if (stockLine.getQuantity() >= blLine.getDelivered()){
                    Long existingPrixLineStock = stockLine.getPrixLineStock();
                    Long existingQuantity = stockLine.getQuantity();
                    Long deliveredQuantity = blLine.getDelivered();
                    stockLine.setQuantity(existingQuantity - deliveredQuantity);
                    stockLine.setPrixLineStock(existingPrixLineStock - (deliveredQuantity * blLine.getPrixArticleHT()));
                    stockRepository.save(stockLine);
                }else {
                    throw new RuntimeException("Stock insuffisent");
                }


                BCClientLine bcClientLine = blLine.getBcClientLine();
                if (bcClientLine != null) {
                    bcClientLine.setDelivered(bcClientLine.getDelivered() + blLine.getDelivered());
                    bcClientLine.setRemaining(bcClientLine.getRemaining() - blLine.getDelivered());
                    bcClientLineRepository.save(bcClientLine);
                }
            }
        }
    }
}
