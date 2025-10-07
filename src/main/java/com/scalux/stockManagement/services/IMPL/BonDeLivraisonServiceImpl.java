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
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public BonDeLivraisonDTO addBL(CreateBLDTO createBLDTO, Long id) {
        BonDeLivraison bl = new BonDeLivraison();
        BonDeCommande bc = bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de commande not found"));
        bl.setBc(bc);
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
//        if (deliverDTO.getDeliveredQuantity() <= bcLine.getRemaining()) {
            blLine.setDelivered(deliverDTO.getDeliveredQuantity() + blLine.getDelivered());
            blLine.setRemainingAfter(blLine.getRemainingBefore() - deliverDTO.getDeliveredQuantity());
            blLineRepository.save(blLine);

            BCLine bcLine = blLine.getBcLine();
            if (bcLine != null) {
                bcLine.setDelivered(bcLine.getDelivered() + deliverDTO.getDeliveredQuantity());
                bcLine.setRemaining(bcLine.getRemaining() - deliverDTO.getDeliveredQuantity());
                bcLineRepository.save(bcLine);
            }

            stockRepository.findByArticleIdAndColor(blLine.getArticle().getId(), blLine.getColor())
                    .ifPresentOrElse(existingStock -> {
                       existingStock.setQuantity(existingStock.getQuantity() + deliverDTO.getDeliveredQuantity());
                       stockRepository.save(existingStock);
                    },() -> {StockDTO stockDTO = new StockDTO();
                            stockDTO.setColor(blLine.getColor());
                            stockDTO.setQuantity(deliverDTO.getDeliveredQuantity());
                            stockDTO.setArticle(blLine.getArticle());
                            stockRepository.save(stockMapper.toEntity(stockDTO));
                    });
    }

}

