package com.urbantransport.messaging_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbantransport.messaging_service.events.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaConsumerService {

    // Change List<String> to List<Object> in the map
    private final Map<String, List<Object>> topicMessages = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper(); // For JSON to Object conversion

    @KafkaListener(topics = "bus_arrival", groupId = "group_id")
    public void consumeBusArrival(String message) {
        System.out.println("Bus Arrival Message received: " + message);
        try {
            // Deserialize the JSON string into a BusArrival object
            BusArrivalEvent busArrival = objectMapper.readValue(message, BusArrivalEvent.class);
            // Add the object to the list for the 'bus_arrival' topic
            topicMessages.computeIfAbsent("bus_arrival", k -> new ArrayList<>()).add(busArrival);
        } catch (Exception e) {
            System.err.println("Failed to deserialize bus arrival message: " + e.getMessage());
        }
    }
    @KafkaListener(topics = "ticket_purchase", groupId = "group_id")
    public void consumeTicketPurchase(String message) {
        System.out.println("Ticket Purchase Message received: " + message);
        try {
            // Deserialize the JSON string into a TicketPurchase object
            TicketPurchaseEvent ticketPurchase = objectMapper.readValue(message, TicketPurchaseEvent.class);
            // Add the object to the list for the 'ticket_purchase' topic
            topicMessages.computeIfAbsent("ticket_purchase", k -> new ArrayList<>()).add(ticketPurchase);
        } catch (Exception e) {
            System.err.println("Failed to deserialize ticket purchase message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "route_change", groupId = "group_id")
    public void consumeRouteChange(String message) {
        System.out.println("Route Change Message received: " + message);
        try {
            // Deserialize the JSON string into a RouteChange object
            RouteChangeEvent routeChange = objectMapper.readValue(message, RouteChangeEvent.class);
            // Add the object to the list for the 'route_change' topic
            topicMessages.computeIfAbsent("route_change", k -> new ArrayList<>()).add(routeChange);
        } catch (Exception e) {
            System.err.println("Failed to deserialize route change message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "delay_notification", groupId = "group_id")
    public void consumeDelayNotification(String message) {
        System.out.println("Delay Notification Message received: " + message);
        try {
            // Deserialize the JSON string into a DelayNotification object
            DelayNotificationEvent delayNotification = objectMapper.readValue(message, DelayNotificationEvent.class);
            // Add the object to the list for the 'delay_notification' topic
            topicMessages.computeIfAbsent("delay_notification", k -> new ArrayList<>()).add(delayNotification);
        } catch (Exception e) {
            System.err.println("Failed to deserialize delay notification message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "subscription_success", groupId = "group_id")
    public void consumeSubscriptionSuccess(String message) {
        System.out.println("Subscription Success Message received: " + message);
        try {
            // Deserialize the JSON string into a SubscriptionSuccess object
            SubscriptionSuccessEvent subscriptionSuccess = objectMapper.readValue(message, SubscriptionSuccessEvent.class);
            // Add the object to the list for the 'subscription_success' topic
            topicMessages.computeIfAbsent("subscription_success", k -> new ArrayList<>()).add(subscriptionSuccess);
        } catch (Exception e) {
            System.err.println("Failed to deserialize subscription success message: " + e.getMessage());
        }
    }

    @KafkaListener(topics = "registration_success", groupId = "group_id")
    public void consumeRegistrationSuccess(String message) {
        System.out.println("Registration Success Message received: " + message);
        try {
            // Deserialize the JSON string into a RegistrationSuccess object
            RegistrationSuccessEvent registrationSuccess = objectMapper.readValue(message, RegistrationSuccessEvent.class);
            // Add the object to the list for the 'registration_success' topic
            topicMessages.computeIfAbsent("registration_success", k -> new ArrayList<>()).add(registrationSuccess);
        } catch (Exception e) {
            System.err.println("Failed to deserialize registration success message: " + e.getMessage());
        }
    }

    // Change return type to List<Object> as the map now holds Object instead of String
    public List<Object> getMessagesForTopic(String topic) {
        return topicMessages.getOrDefault(topic, Collections.emptyList());
    }

    // Change return type to Map<String, List<Object>> to match the modified map structure
    public Map<String, List<Object>> getAllTopicMessages() {
        return topicMessages;
    }
}
