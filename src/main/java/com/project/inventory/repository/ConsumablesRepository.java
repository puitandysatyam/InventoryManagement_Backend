package com.project.inventory.repository;

import com.project.inventory.entity.Consumables;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsumablesRepository extends MongoRepository<Consumables, String> {
}
