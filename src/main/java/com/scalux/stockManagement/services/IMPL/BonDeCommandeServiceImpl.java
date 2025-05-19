package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.BonDeCommandeDTO;
import com.scalux.stockManagement.entities.Article;
import com.scalux.stockManagement.entities.BCLine;
import com.scalux.stockManagement.entities.BonDeCommande;
import com.scalux.stockManagement.mappers.BonDeCommandeMapper;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.repositories.BCLineRepository;
import com.scalux.stockManagement.repositories.BonDeCommandeRepository;
import com.scalux.stockManagement.services.IBonDeCommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonDeCommandeServiceImpl implements IBonDeCommandeService {

    private final BonDeCommandeRepository bcRepository;
    private final ArticleRepository articleRepository;
    private final BonDeCommandeMapper bcMapper;
    private final BCLineRepository bcLineRepository;

    @Override
    public BonDeCommandeDTO addBC(BonDeCommandeDTO dto) {
        BonDeCommande bc = new BonDeCommande();
        bc.setDate(dto.getDate());
        List<BCLine> lignes = new ArrayList<>();
        BigDecimal prixTotal = BigDecimal.ZERO;

        for (BCLineDTO lineDTO : dto.getLignes()) {
            Article article = articleRepository.findById(lineDTO.getArticleId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setColor(lineDTO.getColor());
            line.setSupplierReference(lineDTO.getSupplierReference());
            line.setBc(bc);

            BigDecimal lineTotal = article.getPrixUnitaireHT().multiply(BigDecimal.valueOf(line.getQuantity()));
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal.add(lineTotal);
        }

        bc.setLignes(lignes);
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

        bcLineRepository.deleteAll(existing.getLignes());
        existing.getLignes().clear();
        existing.setDate(dto.getDate());

        BigDecimal prixTotal = BigDecimal.ZERO;
        List<BCLine> lignes = new ArrayList<>();

        for (BCLineDTO lineDTO : dto.getLignes()) {
            Article article = articleRepository.findById(lineDTO.getArticleId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCLine line = new BCLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setColor(lineDTO.getColor());
            line.setSupplierReference(lineDTO.getSupplierReference());
            line.setBc(existing);

            BigDecimal lineTotal = article.getPrixUnitaireHT().multiply(BigDecimal.valueOf(line.getQuantity()));
            line.setPrixTotalLigne(lineTotal);

            lignes.add(line);
            prixTotal = prixTotal.add(lineTotal);
        }

        existing.setLignes(lignes);
        existing.setPrixTotalHT(prixTotal);

        return bcMapper.toDto(bcRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        bcRepository.deleteById(id);
    }
}

