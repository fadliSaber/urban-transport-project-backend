package com.urbantransport.notification_service.service;

import com.urbantransport.notification_service.entity.Notification;
import com.urbantransport.notification_service.event.BusArrivalEvent;
import com.urbantransport.notification_service.event.DelayNotificationEvent;
import com.urbantransport.notification_service.event.SubscriptionSuccessEvent;
import com.urbantransport.notification_service.event.TicketPurchaseEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@EnableKafka
public class NotificationsService {

    private static final String NOTIFICATIONS_KEY_PREFIX = "user:notifications:";

    private final RedisTemplate<String, Object> redisTemplate;

    // Store notifications temporarily
    private final List<String> messages = new ArrayList<>();

    // Constructor to inject RedisTemplate
    public NotificationsService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Listen to bus arrival events from Kafka topic "busArrivalTopic"
    @KafkaListener(topics = "busArrivalTopic", groupId = "notification-service-group")
    public void listenBusArrivalTopic(BusArrivalEvent busArrivalEvent) {
        System.out.println("Received BusArrivalEvent: " + busArrivalEvent);

        // Store the event as a message
        String message = "Bus ID: " + busArrivalEvent.getBusId() + ", Arrival Time: " + busArrivalEvent.getArrivalTime();
        synchronized (messages) {
            messages.add(message);
        }
    }

    // Listen to subscription success events from Kafka topic "subscriptionSuccessTopic"
    @KafkaListener(topics = "subscriptionSuccessTopic", groupId = "notification-service-group")
    public void listenSubscriptionSuccessTopic(SubscriptionSuccessEvent subscriptionSuccessEvent) {
        System.out.println("Received SubscriptionSuccessEvent: " + subscriptionSuccessEvent);

        // Store the event as a message
        String message = "User ID: " + subscriptionSuccessEvent.getUserId() + ", Subscription ID: " + subscriptionSuccessEvent.getSubscriptionId() +
                ", Subscription Date: " + subscriptionSuccessEvent.getSubscriptionDate();
        synchronized (messages) {
            messages.add(message);
        }
    }

    // Listen to ticket purchase events from Kafka topic "ticketPurchaseTopic"
    @KafkaListener(topics = "ticketPurchaseTopic", groupId = "notification-service-group")
    public void listenTicketPurchaseTopic(TicketPurchaseEvent ticketPurchaseEvent) {
        System.out.println("Received TicketPurchaseEvent: " + ticketPurchaseEvent);

        // Store the event as a message
        String message = "User ID: " + ticketPurchaseEvent.getUserId() + ", Ticket ID: " + ticketPurchaseEvent.getTicketId() +
                ", Purchase Time: " + ticketPurchaseEvent.getPurchaseTime();
        synchronized (messages) {
            messages.add(message);
        }
    }

    // Listen to delay notification events from Kafka topic "delayNotificationTopic"
    @KafkaListener(topics = "delayNotificationTopic", groupId = "notification-service-group")
    public void listenDelayNotificationTopic(DelayNotificationEvent delayNotificationEvent) {
        System.out.println("Received DelayNotificationEvent: " + delayNotificationEvent);

        // Store the event as a message
        String message = "Bus ID: " + delayNotificationEvent.getBusId() + ", Delay Reason: " + delayNotificationEvent.getDelayReason() +
                ", New ETA: " + delayNotificationEvent.getNewEta();
        synchronized (messages) {
            messages.add(message);
        }
    }

    // Method to retrieve stored messages (notifications)
    public List<String> getMessages() {
        synchronized (messages) {
            return new ArrayList<>(messages);
        }
    }

    // Method to clear all stored messages
    public void clearMessages() {
        synchronized (messages) {
            messages.clear();
        }
    }

    // Optional: Method to save notifications to Redis
    public void saveNotificationToRedis(String userId, String message) {
        String key = NOTIFICATIONS_KEY_PREFIX + userId;
        redisTemplate.opsForList().rightPush(key, message);  // Store notifications in Redis
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);  // Set an expiration time (e.g., 30 minutes)
    }

    public List<String> getNotificationsFromRedis(String userId) {
        String key = NOTIFICATIONS_KEY_PREFIX + userId;

        List<Object> rawMessages = redisTemplate.opsForList().range(key, 0, -1); // Retrieve all notifications for the user
        List<String> messages = new ArrayList<>();

        if (rawMessages != null) {
            for (Object message : rawMessages) {
                if (message instanceof String) {
                    messages.add((String) message);  // Cast the Object to String
                }
            }
        }

        return messages;
    }
}