package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.mappers.BonDeCommandeMapper;
import com.scalux.stockManagement.mappers.BonDeLivraisonMapper;
import com.scalux.stockManagement.mappers.StockMapper;
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

    private final BonDeLivraisonMapper blMapper;
    private final BLLineRepository blLineRepository;
    private final BonDeLivraisonRepository blRepository;
    private final BonDeCommandeRepository bcRepository;
    private final BCLineRepository bcLineRepository;
    private final BonDeCommandeMapper bcMapper;
    private final ArticleMapper articleMapper;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public BonDeLivraisonDTO addBL(CreateBLDTO createBLDTO, Long id) {
        BonDeLivraison bl = new BonDeLivraison();
        BonDeCommande bc = bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de commande not found"));
        bl.setBc(bc);
        LocalDate localDate = LocalDate.now();
        bl.setDate(localDate);
        bl.setReference(createBLDTO.getReference());
        List<BLLine> lines = new ArrayList<>();
        Long prixTotal = 0L;
        BonDeCommandeDTO bcDto = bcMapper.toDto(bc);

        for (BCLineDTO bcLineDTO : bcDto.getLines()){


            BLLine line = new BLLine();
            line.setArticle(articleMapper.toEntity(bcLineDTO.getArticle()));
            line.setColor(bcLineDTO.getColor());
            line.setQuantity(bcLineDTO.getQuantity());
            line.setRemainingBefore(bcLineDTO.getRemaining());

            line.setDelivered(0L);
            BCLine bcLine = bcLineRepository.findById(bcLineDTO.getId()).orElseThrow();
            line.setBcLine(bcLine);
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
                .orElseThrow(() -> new RuntimeException("BL not found")));
    }


    @Override
    public void deliver(DeliverDTO deliverDTO) {

        BLLine blLine = blLineRepository.findById(deliverDTO.getId()).orElse(null);
        if (blLine.getBl().getIsValidated() == false){
            blLine.setDelivered(deliverDTO.getDeliveredQuantity() + blLine.getDelivered());
            blLine.setRemainingAfter(blLine.getRemainingBefore() - deliverDTO.getDeliveredQuantity());
            blLineRepository.save(blLine);
        }else {
            throw new RuntimeException("BL Already validated");
        }

    }

    @Override
    public void validate(Long id) {
        BonDeLivraison bonDeLivraison = blRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de livraison not found"));


        bonDeLivraison.setIsValidated(true);
        blRepository.save(bonDeLivraison);

        for (BLLine blLine : bonDeLivraison.getLines()) {
            if (blLine.getDelivered() != 0) {
                stockRepository.findByArticleIdAndColor(blLine.getArticle().getId(), blLine.getColor())
                        .ifPresentOrElse(existingStock -> {
                            existingStock.setQuantity(existingStock.getQuantity() + blLine.getDelivered());
                            stockRepository.save(existingStock);
                        }, () -> {
                            StockDTO stockDTO = new StockDTO();
                            stockDTO.setColor(blLine.getColor());
                            stockDTO.setQuantity(blLine.getDelivered());
                            stockDTO.setArticle(blLine.getArticle());
                            stockRepository.save(stockMapper.toEntity(stockDTO));
                        });
                BCLine bcLine = blLine.getBcLine();
                if (bcLine != null) {
                    bcLine.setDelivered(bcLine.getDelivered() + blLine.getDelivered());
                    bcLine.setRemaining(bcLine.getRemaining() - blLine.getDelivered());
                    bcLineRepository.save(bcLine);
                }
            }
        }
    }


}

