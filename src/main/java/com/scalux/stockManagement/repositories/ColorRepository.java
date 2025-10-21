package com.scalux.stockManagement.repositories;

import com.scalux.stockManagement.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
}
