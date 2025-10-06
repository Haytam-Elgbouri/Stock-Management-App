package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.mappers.BonDeCommandeMapper;
import com.scalux.stockManagement.mappers.BonDeLivraisonMapper;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.repositories.BonDeCommandeRepository;
import com.scalux.stockManagement.repositories.BonDeLivraisonRepository;
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
    private final BonDeLivraisonRepository blRepository;
    private final BonDeCommandeRepository bcRepository;
    private final BonDeCommandeMapper bcMapper;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

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
