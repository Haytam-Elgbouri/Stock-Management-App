package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.ArticleMapper;
import com.scalux.stockManagement.mappers.BonDeCommandeClientMapper;
import com.scalux.stockManagement.mappers.BonDeLivraisonMapper;
import com.scalux.stockManagement.mappers.ColorMapper;
import com.scalux.stockManagement.repositories.BCClientLineRepository;
import com.scalux.stockManagement.repositories.BonDeCommandeClientRepository;
import com.scalux.stockManagement.repositories.BonDeLivraisonRepository;
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
//    private final
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
//            line.setPrixArticleHT(bcClientLineDTO.getPrixArticleHT());

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
}
