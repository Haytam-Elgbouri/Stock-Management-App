package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.Partner;
import com.scalux.stockManagement.enums.PartnerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<List<Partner>> findByPartnerType(PartnerType partnerType);
}
