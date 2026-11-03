package com.scalux.stockManagement.config;



import com.scalux.stockManagement.entities.Color;
import com.scalux.stockManagement.repositories.ColorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initColors(ColorRepository colorRepository) {
        return args -> {
            if (colorRepository.count() == 0) {
                colorRepository.save(new Color("Brut"));
                colorRepository.save(new Color("Noir"));
            }
        };
    }
}