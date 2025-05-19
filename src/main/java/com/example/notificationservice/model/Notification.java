package com.example.notificationservice.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Document(collection = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notification {
    @Id
    private String id;


    private String userId;
    private String message;
    private NotificationType type;
    private String status;
    private LocalDateTime timestamp;
}
