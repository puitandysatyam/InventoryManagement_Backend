package com.project.inventory.repository;

import com.project.inventory.entity.Scrap;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScrapRepository extends MongoRepository<Scrap, String> {
}
