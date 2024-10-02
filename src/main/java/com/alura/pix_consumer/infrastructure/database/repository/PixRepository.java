package com.alura.pix_consumer.infrastructure.database.repository;

import com.alura.pix_consumer.infrastructure.database.model.Pix;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<Pix, Integer> {
    Pix findByIdentifier(String identifier);
}
