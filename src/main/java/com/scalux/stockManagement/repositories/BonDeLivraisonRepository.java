package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.BonDeLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonDeLivraisonRepository extends JpaRepository<BonDeLivraison, Long> {
    boolean existsByBcClientIdAndIsValidatedFalse(Long bcClientId);
}
