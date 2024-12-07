package com.urbantransport.notification_service.service;

import com.urbantransport.notification_service.entity.Notification;
import com.urbantransport.notification_service.event.BusArrivalEvent;
import com.urbantransport.notification_service.event.DelayNotificationEvent;
import com.urbantransport.notification_service.event.SubscriptionSuccessEvent;
import com.urbantransport.notification_service.event.TicketPurchaseEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
@Service

public class NotificationsService {
    private static final String NOTIFICATIONS_KEY_PREFIX = "user:notifications:";

    private final RedisTemplate<String,Object> redisTemplate;

    public NotificationsService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics = "busArrivalTopic", groupId = "notification-service-group")
    public void listenBusArrivalTopic(String message) {
        System.out.println("Received message from busArrivalTopic: " + message);

        // Store the message in the list
        synchronized (messages) {
            messages.add(message);
        }
    }

    public List<String> getMessages() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    public void clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
    }
}
