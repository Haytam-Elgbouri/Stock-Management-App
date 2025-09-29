package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.BLLineDTO;
import com.scalux.stockManagement.dtos.BonDeLivraisonDTO;
import com.scalux.stockManagement.entities.*;
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
    private final ArticleRepository articleRepository;

    @Override
    public BonDeLivraisonDTO addBL(BonDeLivraisonDTO bonDeLivraisonDTO, Long id) {
        BonDeLivraison bl = new BonDeLivraison();
        bl.setReference(bonDeLivraisonDTO.getReference());
        BonDeCommande bc = bcRepository.findById(id).orElse(null);
        bl.setBc(bc);
        List<BLLine> lines = new ArrayList<>();
        Long prixTotal = 0L;


        for (BLLineDTO lineDTO : bonDeLivraisonDTO.getLines()) {
            Long articleId = lineDTO.getArticle() != null ? lineDTO.getArticle().getId() : null;
            if (articleId == null) {
                throw new RuntimeException("Article ID is required");
            }

            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BLLine line = new BLLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setDelivered(0L);
            line.setColor(lineDTO.getColor());
//            line.setBl(bl);

            Long lineTotal = article.getPrixTotalHT() * line.getQuantity();
            line.setPrixTotalLigne(lineTotal);

            lines.add(line);
            prixTotal = prixTotal + lineTotal;
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
}
