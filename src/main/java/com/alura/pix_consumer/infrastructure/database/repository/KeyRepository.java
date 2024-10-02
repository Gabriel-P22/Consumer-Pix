package com.alura.pix_consumer.infrastructure.database.repository;

import com.alura.pix_consumer.infrastructure.database.model.Key;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyRepository extends JpaRepository<Key, Integer> {
    Key findByKey(String key);
}
