package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.BCClientLineDTO;
import com.scalux.stockManagement.dtos.BCLineDTO;
import com.scalux.stockManagement.dtos.BonDeCommandeClientDTO;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.BCClientLineMapper;
import com.scalux.stockManagement.mappers.BonDeCommandeClientMapper;
import com.scalux.stockManagement.repositories.ArticleColorPriceRepository;
import com.scalux.stockManagement.repositories.ArticleRepository;
import com.scalux.stockManagement.repositories.BCClientLineRepository;
import com.scalux.stockManagement.repositories.BonDeCommandeClientRepository;
import com.scalux.stockManagement.services.IBonDeCommandeClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class BonDeCommandeClientServiceImpl implements IBonDeCommandeClientService {

    private final BonDeCommandeClientRepository bccRepository;
    private final BonDeCommandeClientMapper bccMapper;
    private final BCClientLineRepository bcClientLineRepository;
    private final BCClientLineMapper bcClientLineMapper;
    private final ArticleRepository articleRepository;
    private final ArticleColorPriceRepository articleColorPriceRepository;

    @Override
    public BonDeCommandeClientDTO addBcc(BonDeCommandeClientDTO dto) {
        BonDeCommandeClient bcc = new BonDeCommandeClient();
        bcc.setReference(dto.getReference());
        bcc.setDate(LocalDate.now());
        bcc.setClient(dto.getClient());

        List<BCClientLine> lines = new ArrayList<>();
        Long prixTotal = 0L;

        for (BCClientLineDTO lineDTO : dto.getLines()) {
            if (lineDTO.getArticle() == null || lineDTO.getArticle().getId() == null) {
                throw new RuntimeException("Article ID is required");
            }

            Article article = articleRepository.findById(lineDTO.getArticle().getId())
                    .orElseThrow(() -> new RuntimeException("Article not found"));

            BCClientLine line = new BCClientLine();
            line.setArticle(article);
            line.setQuantity(lineDTO.getQuantity());
            line.setRemaining(lineDTO.getQuantity());
            line.setDelivered(0L);
            line.setBcClient(bcc);

            if (lineDTO.getColor().getId() != null) {
                ArticleColorPrice colorPrice = articleColorPriceRepository
                        .findByArticleIdAndColorId(article.getId(), lineDTO.getColor().getId())
                        .orElseThrow(() -> new RuntimeException("Color price not found"));

                line.setColor(colorPrice.getColor());

                Long lineTotal = colorPrice.getPrixTotalHT() * line.getQuantity();
                line.setPrixTotalLigne(lineTotal);

                prixTotal += lineTotal;
            } else {
                throw new RuntimeException("Color is required for each BC line");
            }

            lines.add(line);
        }

        bcc.setLines(lines);
        bcc.setPrixTotalHT(prixTotal);

        BonDeCommandeClient saved = bccRepository.save(bcc);

        return bccMapper.toDto(saved);
    }

    @Override
    public List<BonDeCommandeClientDTO> getAll() {
        return bccRepository.findAll().stream()
                .map(bccMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonDeCommandeClientDTO getById(Long id) {
        return bccMapper.toDto(bccRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BC Client not found")));
    }
}
