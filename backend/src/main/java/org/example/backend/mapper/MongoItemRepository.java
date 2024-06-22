package org.example.backend.mapper;

import org.example.backend.entity.MongoItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoItemRepository extends MongoRepository<MongoItem, String> {
}