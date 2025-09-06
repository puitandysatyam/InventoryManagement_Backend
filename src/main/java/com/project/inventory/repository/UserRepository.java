package com.project.inventory.repository;

import com.project.inventory.entity.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<Users, String> {

    public Optional<Users> findByUsername(String username);
}


