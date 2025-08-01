package com.project.inventory.repository;

import com.project.inventory.entity.Electronics;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElectronicsRepository extends MongoRepository<Electronics, String> {
}
