package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.BRLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BRLineRepository extends JpaRepository<BRLine, Long> {
}
