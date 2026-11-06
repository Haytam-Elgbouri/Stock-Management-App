package com.scalux.stockManagement.services;

import com.scalux.stockManagement.entities.Partner;
import com.scalux.stockManagement.enums.PartnerType;

import java.util.List;

public interface IPartnerService {
    Partner addPartner(Partner partner);
//    Partner getPartner(Long id);
    List<Partner> getAllPartners(PartnerType partnerType);

    Partner updatePartner(Partner partner, Long id);

    void deletePartner(Long id);
}
