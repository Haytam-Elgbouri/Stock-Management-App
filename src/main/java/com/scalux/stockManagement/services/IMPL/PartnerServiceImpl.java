package com.scalux.stockManagement.services.IMPL;

import com.scalux.stockManagement.entities.Partner;
import com.scalux.stockManagement.enums.PartnerType;
import com.scalux.stockManagement.repositories.PartnerRepository;
import com.scalux.stockManagement.services.IPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements IPartnerService {

    private final PartnerRepository partnerRepository;

    @Override
    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

//    @Override
//    public Partner getPartner(Long id) {
//        return partnerRepository.findById(id).or;
//    }

    @Override
    public List<Partner> getAllPartners(PartnerType partnerType) {
        return partnerRepository.findByPartnerType(partnerType);
    }

    @Override
    public Partner updatePartner(Partner partner, Long id) {
        Partner existingPartner = partnerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("partner not found"));
        existingPartner.setName(partner.getName());
        existingPartner.setPartnerType(partner.getPartnerType());
        return partnerRepository.save(existingPartner);
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }
}
