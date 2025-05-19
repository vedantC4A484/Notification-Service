package com.example.notificationservice.queue;

import java.time.LocalDateTime;

import javax.management.RuntimeErrorException;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.notificationservice.config.RabbitMQConfig;
import com.example.notificationservice.model.Notification;
import com.example.notificationservice.model.NotificationRequest;
import com.example.notificationservice.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository notificationRepository;
    @RabbitListener(queues=RabbitMQConfig.NOTIFICATION_QUEUE,containerFactory = "rabbitListenerContainerFactory")
    public void handleNotification(NotificationRequest request){
        log.info("Recieved Notification:{}",request);
        try{
            if("fail".equalsIgnoreCase(request.getMessage())){
                throw new RuntimeException("Simulated failure");
            }
        

        Notification notification=new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setStatus("SENT");
        notification.setTimestamp(LocalDateTime.now());

        notificationRepository.save(notification);

        switch(request.getType()){
            case EMAIL-> log.info("Email sent to user {}:{}",request.getUserId(),request.getMessage());
            case SMS-> log.info("SMS sent to user{}:{}",request.getUserId(),request.getMessage());
            case IN_APP->log.info("In-App Notification created for user{}:{}",request.getUserId(),request.getMessage());

        }}
        catch(Exception e){
            log.error("Failed to process notification:{}",e.getMessage());
            throw e;
        }

    }
}
