package com.example.notificationservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.notificationservice.model.Notification;

@Repository
public interface NotificationRepository extends MongoRepository<Notification,String> {
    List<Notification> findByUserId(String userId);


    
}
