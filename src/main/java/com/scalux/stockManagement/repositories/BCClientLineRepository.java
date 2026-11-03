package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.BCClientLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BCClientLineRepository extends JpaRepository<BCClientLine, Long> {
}
