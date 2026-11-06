package com.scalux.stockManagement.controllers;

import com.scalux.stockManagement.entities.Partner;
import com.scalux.stockManagement.enums.PartnerType;
import com.scalux.stockManagement.services.IPartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/partner")
public class PartnerController {
    private final IPartnerService partnerService;


    @PostMapping
    Partner addPartner(@RequestBody Partner partner) {
        return partnerService.addPartner(partner);
    }

    @GetMapping
    Optional<List<Partner>> getAllPartners(@RequestParam PartnerType type) {
        return partnerService.getAllPartners(type);
    }

    @PutMapping
    Partner updatePartner(@RequestBody Partner partner,  Long id) {
        return partnerService.updatePartner(partner, id);
    }

    @DeleteMapping
    void deletePartner(@RequestBody Long id) {
        partnerService.deletePartner(id);
    }
}
