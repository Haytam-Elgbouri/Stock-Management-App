package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.*;
import com.scalux.stockManagement.repositories.*;
import com.scalux.stockManagement.services.IBonDeReceptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BonDeReceptionServiceImpl implements IBonDeReceptionService {

    private final BonDeReceptionMapper brMapper;
    private final BRLineRepository brLineRepository;
    private final BonDeReceptionRepository brRepository;
    private final BonDeCommandeRepository bcRepository;
    private final BCLineRepository bcLineRepository;
    private final BonDeCommandeMapper bcMapper;
    private final ArticleMapper articleMapper;
    private final StockRepository stockRepository;
    private final StockMapper stockMapper;
    private final ColorMapper colorMapper;

    @Override
    public BonDeReceptionDTO addBR(CreateBRDTO createBRDTO, Long id) {
        boolean hasUnvalidatedBR = brRepository.existsByBcIdAndIsValidatedFalse(id);

        if (hasUnvalidatedBR) {
            throw new RuntimeException("Cannot create a new BR until the previous BR is validated.");
        }
        BonDeReception br = new BonDeReception();
        BonDeCommande bc = bcRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de commande not found"));
        br.setBc(bc);
        LocalDate localDate = LocalDate.now();
        br.setDate(localDate);
        br.setReference(createBRDTO.getReference());
        List<BRLine> lines = new ArrayList<>();
        Long prixTotal = 0L;
        BonDeCommandeDTO bcDto = bcMapper.toDto(bc);

        int count = 0;

        for (BCLineDTO bcLineDTO : bcDto.getLines()){

            if (bcLineDTO.getReceived() >= bcLineDTO.getQuantity()){
                count++;
            }

            BRLine line = new BRLine();
            line.setArticle(articleMapper.toEntity(bcLineDTO.getArticle()));
            line.setColor(colorMapper.toEntity(bcLineDTO.getColor()));
            line.setQuantity(bcLineDTO.getQuantity());
            line.setRemainingBefore(bcLineDTO.getRemaining());
            line.setPrixArticleHT(bcLineDTO.getPrixArticleHT());

            line.setReceived(0L);
            BCLine bcLine = bcLineRepository.findById(bcLineDTO.getId()).orElseThrow();
            line.setBcLine(bcLine);
            line.setBr(br);

            lines.add(line);

        }

        if (count == bcDto.getLines().size()){
            System.out.println("count : " + count + " lines : " + bcDto.getLines().size());
            throw new RuntimeException("all lines are received");
        }

        br.setLines(lines);
        br.setPrixTotalHT(prixTotal);
        br.setIsValidated(false);

        BonDeReception saved = brRepository.save(br);

    return brMapper.toDto(saved);

    }

    @Override
    public List<BonDeReceptionDTO> getAll() {
        return brRepository.findAll().stream()
                .map(brMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BonDeReceptionDTO getById(Long id) {
        return brMapper.toDto(brRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BR not found")));
    }

    @Override
    public void receiveBR(BRRecieveDTO brRecieveDTO) {

        BonDeReception br = brRepository.findById(brRecieveDTO.getBrId())
                .orElseThrow(() -> new RuntimeException("BR not found"));

        if (Boolean.TRUE.equals(br.getIsValidated())) {
            throw new RuntimeException("BR Already validated");
        }

        boolean empty = false;

        for (BRLineReceiveDTO lineDTO : brRecieveDTO.getLines()) {
            BRLine line = brLineRepository.findById(lineDTO.getId())
                    .orElseThrow(() -> new RuntimeException("BRLine not found: " + lineDTO.getId()));
            if (line.getRemainingBefore()<=0){
                throw new RuntimeException("BRLine already received");
            }

            line.setReceived(lineDTO.getReceivedQuantity() + line.getReceived());
            line.setRemainingAfter(line.getRemainingBefore() - lineDTO.getReceivedQuantity());
            if (lineDTO.getReceivedQuantity()!=null && lineDTO.getReceivedQuantity()>0) {
                empty = true;
            }
            brLineRepository.save(line);
        }
        if (!empty) {
            throw new RuntimeException("BR is empty");
        }
    }



    @Override
    public void validate(Long id) {
        BonDeReception bonDeReception = brRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de Reception not found"));

        boolean empty = false;

        bonDeReception.setIsValidated(true);
        brRepository.save(bonDeReception);

        for (BRLine BRLine : bonDeReception.getLines()) {
            if (BRLine.getReceived() != null && BRLine.getReceived() != 0) {
                stockRepository.findByArticleIdAndColorId(BRLine.getArticle().getId(),BRLine.getColor().getId()
                        )
                        .ifPresentOrElse(existingStock -> {
                            Long existingPrixLineStock = existingStock.getPrixLineStock();
                            Long existingQuantity = existingStock.getQuantity();
                            Long receivedQuantity = BRLine.getReceived();
                            existingStock.setQuantity(existingQuantity + receivedQuantity);
                            existingStock.setPrixLineStock(existingPrixLineStock + (receivedQuantity * BRLine.getPrixArticleHT()));
                            stockRepository.save(existingStock);
                        }, () -> {
                            StockDTO stockDTO = new StockDTO();
                            stockDTO.setColor(colorMapper.toDto(BRLine.getColor()));
                            Long articlePrice = BRLine.getPrixArticleHT();
                            stockDTO.setPrixArticleHT(articlePrice);
                            Long quantity = BRLine.getReceived();
                            stockDTO.setQuantity(quantity);
                            stockDTO.setArticle(articleMapper.toDto(BRLine.getArticle()));
                            stockDTO.setPrixLineStock(quantity * articlePrice);
                            stockRepository.save(stockMapper.toEntity(stockDTO));
                        });
                BCLine bcLine = BRLine.getBcLine();
                if (bcLine != null) {
                    bcLine.setReceived(bcLine.getReceived() + BRLine.getReceived());
                    bcLine.setRemaining(bcLine.getRemaining() - BRLine.getReceived());
                    bcLineRepository.save(bcLine);
                }

                empty = true;

            }
        }
        if (!empty) {
            throw new RuntimeException("BR is empty");
        }
    }

    @Override
    public void delete(Long id) {
        BonDeReception br = brRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("BR found"));
        if (Boolean.TRUE.equals(br.getIsValidated())) {
            throw new RuntimeException("validated BRs cannot be deleted");
        }
        brRepository.deleteById(id);
    }


}

