package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.dtos.*;
import com.scalux.stockManagement.entities.*;
import com.scalux.stockManagement.mappers.*;
import com.scalux.stockManagement.repositories.*;
import com.scalux.stockManagement.services.IBonDeReceptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BonDeReceptionServiceImpl implements IBonDeReceptionService {

    private final BonDeReceptionMapper brMapper;
    private final BLLineRepository brLineRepository;
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

        for (BCLineDTO bcLineDTO : bcDto.getLines()){


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


//    @Override
//    public void deliver(RecieveDTO recieveDTO) {
//
//        BRLine BRLine = brLineRepository.findById(recieveDTO.getId()).orElse(null);
//        if (BRLine.getBr().getIsValidated() == false){
//            BRLine.setReceived(recieveDTO.getReceivedQuantity() + BRLine.getReceived());
//            BRLine.setRemainingAfter(BRLine.getRemainingBefore() - recieveDTO.getReceivedQuantity());
//            brLineRepository.save(BRLine);
//        }else {
//            throw new RuntimeException("BR Already validated");
//        }
//
//    }

    @Override
    public void receiveBR(BRRecieveDTO brRecieveDTO) {

        BonDeReception br = brRepository.findById(brRecieveDTO.getBrId())
                .orElseThrow(() -> new RuntimeException("BR not found"));

        if (Boolean.TRUE.equals(br.getIsValidated())) {
            throw new RuntimeException("BR Already validated");
        }

        for (BRLineReceiveDTO lineDTO : brRecieveDTO.getLines()) {
            BRLine line = brLineRepository.findById(lineDTO.getId())
                    .orElseThrow(() -> new RuntimeException("BRLine not found: " + lineDTO.getId()));

            line.setReceived(lineDTO.getReceivedQuantity() + line.getReceived());
            line.setRemainingAfter(line.getRemainingBefore() - lineDTO.getReceivedQuantity());

            brLineRepository.save(line);
        }
    }



    @Override
    public void validate(Long id) {
        BonDeReception bonDeReception = brRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bon de Reception not found"));


        bonDeReception.setIsValidated(true);
        brRepository.save(bonDeReception);

        for (BRLine BRLine : bonDeReception.getLines()) {
            if (BRLine.getReceived() != 0) {
                stockRepository.findByArticleIdAndColorId(BRLine.getArticle().getId(),BRLine.getColor().getId()
                        )
                        .ifPresentOrElse(existingStock -> {
                            existingStock.setQuantity(existingStock.getQuantity() + BRLine.getReceived());
                            stockRepository.save(existingStock);
                        }, () -> {
                            StockDTO stockDTO = new StockDTO();
                            stockDTO.setColor(colorMapper.toDto(BRLine.getColor()));
                            stockDTO.setPrixArticleHT(BRLine.getPrixArticleHT());
                            stockDTO.setQuantity(BRLine.getReceived());
                            stockDTO.setArticle(articleMapper.toDto(BRLine.getArticle()));
                            stockRepository.save(stockMapper.toEntity(stockDTO));
                        });
                BCLine bcLine = BRLine.getBcLine();
                if (bcLine != null) {
                    bcLine.setReceived(bcLine.getReceived() + BRLine.getReceived());
                    bcLine.setRemaining(bcLine.getRemaining() - BRLine.getReceived());
                    bcLineRepository.save(bcLine);
                }
            }
        }
    }


}

