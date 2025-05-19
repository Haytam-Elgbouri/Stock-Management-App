package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.BCLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BCLineRepository extends JpaRepository<BCLine, Long> {}

