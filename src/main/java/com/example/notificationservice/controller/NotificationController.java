package com.example.notificationservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.NotificationRequest;
import com.example.notificationservice.queue.NotificationProducer;
import com.example.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationProducer producer;
    private final NotificationRepository notificationRepository;
    
    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request){
        producer.sendNotification(request);
        return ResponseEntity.ok("Notification Queued Succesfully");
    }
    @GetMapping("/users/{userId}/notifications")
    public ResponseEntity<List<Notification>> getUserNotifications(@PathVariable String userId){
        List<Notification> notifications=notificationRepository.findByUserId(userId);
        return ResponseEntity.ok(notifications);
    }
}
