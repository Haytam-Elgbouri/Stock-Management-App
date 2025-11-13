package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.BLLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BLLineRepository extends JpaRepository<BLLine, Long> {
}
