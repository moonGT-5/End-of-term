package org.example.backend.service;

import org.example.backend.entity.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Feedback saveFeedback(Feedback feedback) {
        return mongoTemplate.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return mongoTemplate.findAll(Feedback.class);
    }
}
