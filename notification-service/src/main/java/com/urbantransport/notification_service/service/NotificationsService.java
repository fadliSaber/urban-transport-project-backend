package com.urbantransport.notification_service.service;

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

    private final List<String> messages = new ArrayList<>();

    public NotificationsService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @KafkaListener(topics = "busArrivalTopic", groupId = "notification-service-group")
    public void listenBusArrivalTopic(BusArrivalEvent busArrivalEvent) {
        System.out.println("Received BusArrivalEvent: " + busArrivalEvent);

        // Store the event as a message
        String message = "Bus ID: " + busArrivalEvent.getBusId() + ", Arrival Time: " + busArrivalEvent.getArrivalTime();
        synchronized (messages) {
            messages.add(message);
        }
    }

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

    @KafkaListener(topics = "ticketPurchaseTopic", groupId = "notification-service-group")
    public void listenTicketPurchaseTopic(TicketPurchaseEvent ticketPurchaseEvent) {
        System.out.println("Received TicketPurchaseEvent: " + ticketPurchaseEvent);

        String message = "User ID: " + ticketPurchaseEvent.getUserId() + ", Ticket ID: " + ticketPurchaseEvent.getTicketId() +
                ", Purchase Time: " + ticketPurchaseEvent.getPurchaseTime();
        synchronized (messages) {
            messages.add(message);
        }
    }

    @KafkaListener(topics = "delayNotificationTopic", groupId = "notification-service-group")
    public void listenDelayNotificationTopic(DelayNotificationEvent delayNotificationEvent) {
        System.out.println("Received DelayNotificationEvent: " + delayNotificationEvent);

        String message = "Bus ID: " + delayNotificationEvent.getBusId()+
                ", New ETA: " + delayNotificationEvent.getNewEta();
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

    public void saveNotificationToRedis(String userId, String message) {
        String key = NOTIFICATIONS_KEY_PREFIX + userId;
        redisTemplate.opsForList().rightPush(key, message);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
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