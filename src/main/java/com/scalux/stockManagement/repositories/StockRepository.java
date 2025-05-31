package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
