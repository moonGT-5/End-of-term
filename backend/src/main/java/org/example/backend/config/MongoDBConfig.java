package org.example.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDBConfig {

    @Bean
    public MongoTemplate mongoTemplate() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost");
        return new MongoTemplate(mongoClient, "feedback");
    }
}


