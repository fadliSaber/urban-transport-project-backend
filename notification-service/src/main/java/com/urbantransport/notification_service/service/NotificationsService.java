package com.urbantransport.notification_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbantransport.notification_service.event.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationsService {


    private final RedisTemplate<String, Object> redisTemplate;
    private static final String NOTIFICATIONS_KEY_PREFIX = "user:notifications:";

    public NotificationsService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private final Map<String, List<Object>> topicMessages = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON to Object conversion

    @KafkaListener(topics = "bus_arrival", groupId = "bus_arrival_group")
    public void consumeBusArrival(String message) {
        System.out.println("Bus Arrival Message received: " + message);
        try {
            // Deserialize the JSON string into a BusArrival object
            BusArrivalEvent busArrival = objectMapper.readValue(message, BusArrivalEvent.class);
            // Add the object to the list for the 'bus_arrival' topic
            topicMessages.computeIfAbsent("bus_arrival", k -> new ArrayList<>()).add(busArrival);
            saveToRedis(busArrival.getUserId(), busArrival);
            System.out.println("Message added to bus_arrival: " + busArrival);

        } catch (Exception e) {
            System.err.println("Failed to deserialize bus arrival message: " + e.getMessage());
        }
    }
    @KafkaListener(topics = "ticket_purchase", groupId = "ticket_purchase_group")
    public void consumeTicketPurchase(String message) {
        System.out.println("Ticket Purchase Message received: " + message);
        try {
            // Deserialize the JSON string into a TicketPurchase object
            TicketPurchaseEvent ticketPurchase = objectMapper.readValue(message, TicketPurchaseEvent.class);
            // Add the object to the list for the 'ticket_purchase' topic
            topicMessages.computeIfAbsent("ticket_purchase", k -> new ArrayList<>()).add(ticketPurchase);
            saveToRedis(ticketPurchase.getUserId(), ticketPurchase);

            System.out.println("Message added to ticketpurchase: " + ticketPurchase);

        } catch (Exception e) {
            System.err.println("Failed to deserialize ticket purchase message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "route_change", groupId = "route_change_group")
    public void consumeRouteChange(String message) {
        System.out.println("Route Change Message received: " + message);
        try {
            // Deserialize the JSON string into a RouteChange object
            RouteChangeEvent routeChange = objectMapper.readValue(message, RouteChangeEvent.class);
            // Add the object to the list for the 'route_change' topic
            topicMessages.computeIfAbsent("route_change", k -> new ArrayList<>()).add(routeChange);
            saveToRedis(routeChange.getUserId(), routeChange);

        } catch (Exception e) {
            System.err.println("Failed to deserialize route change message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "delay_notification", groupId = "delay_notification_group")
    public void consumeDelayNotification(String message) {
        System.out.println("Delay Notification Message received: " + message);
        try {
            // Deserialize the JSON string into a DelayNotification object
            DelayNotificationEvent delayNotification = objectMapper.readValue(message, DelayNotificationEvent.class);
            // Add the object to the list for the 'delay_notification' topic
            topicMessages.computeIfAbsent("delay_notification", k -> new ArrayList<>()).add(delayNotification);
            saveToRedis(delayNotification.getUserId(),delayNotification);

        } catch (Exception e) {
            System.err.println("Failed to deserialize delay notification message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "subscription_success", groupId = "subscription_success_group")
    public void consumeSubscriptionSuccess(String message) {
        System.out.println("Subscription Success Message received: " + message);
        try {
            // Deserialize the JSON string into a SubscriptionSuccess object
            SubscriptionSuccessEvent subscriptionSuccess = objectMapper.readValue(message, SubscriptionSuccessEvent.class);
            // Add the object to the list for the 'subscription_success' topic
            topicMessages.computeIfAbsent("subscription_success", k -> new ArrayList<>()).add(subscriptionSuccess);
            saveToRedis(subscriptionSuccess.getUserId(),subscriptionSuccess);

        } catch (Exception e) {
            System.err.println("Failed to deserialize subscription success message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "registration_success", groupId = "registration_success_group")
    public void consumeRegistrationSuccess(String message) {
        System.out.println("Registration Success Message received: " + message);
        try {
            // Deserialize the JSON string into a RegistrationSuccess object
            RegistrationSuccessEvent registrationSuccess = objectMapper.readValue(message, RegistrationSuccessEvent.class);
            // Add the object to the list for the 'registration_success' topic
            topicMessages.computeIfAbsent("registration_success", k -> new ArrayList<>()).add(registrationSuccess);
            saveToRedis(registrationSuccess.getUserId(),registrationSuccess);

        } catch (Exception e) {
            System.err.println("Failed to deserialize registration success message: " + e.getMessage());
        }
    }
    public List<Object> getNotificationsForUser(String userId) {
        String key = "user:" + userId + ":notifications";
        List<Object> notifications = redisTemplate.opsForList().range(key, 0, -1); // Retrieve all notifications in the list
        return notifications != null ? notifications : Collections.emptyList(); // Return an empty list if no notifications exist
    }

    public List<Object> getMessagesForTopic(String topic) {
        return topicMessages.getOrDefault(topic, Collections.emptyList());
    }
    private void saveToRedis(String userId, Object event) {
        String key = "user:" + userId + ":notifications";
        redisTemplate.opsForList().rightPush(key, event);
        redisTemplate.expire(key, 1, TimeUnit.HOURS);
        System.out.println("Saved event to Redis for userId " + userId + ": " + event);
    }


    public Map<String, List<Object>> getAllTopicMessages() {
        return topicMessages;
    }
}


