package com.urbantransport.messaging_service.service;

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

    @KafkaListener(topics = "bus_arrival", groupId = "group_id")
    public void consumeBusArrival(String message) {
        System.out.println("Bus Arrival Message received: " + message);
        // Add message as Object in the list for the topic
        topicMessages.computeIfAbsent("bus_arrival", k -> new ArrayList<>()).add(message);
    }

    @KafkaListener(topics = "ticket_purchase", groupId = "group_id")
    public void consumeTicketPurchase(String message) {
        System.out.println("Ticket Purchase Message received: " + message);
        topicMessages.computeIfAbsent("ticket_purchase", k -> new ArrayList<>()).add(message);
    }

    @KafkaListener(topics = "route_change", groupId = "group_id")
    public void consumeRouteChange(String message) {
        System.out.println("Route Change Message received: " + message);
        topicMessages.computeIfAbsent("route_change", k -> new ArrayList<>()).add(message);
    }

    @KafkaListener(topics = "delay_notification", groupId = "group_id")
    public void consumeDelayNotification(String message) {
        System.out.println("Delay Notification Message received: " + message);
        topicMessages.computeIfAbsent("delay_notification", k -> new ArrayList<>()).add(message);
    }

    @KafkaListener(topics = "subscription_success", groupId = "group_id")
    public void consumeSubscriptionSuccess(String message) {
        System.out.println("Subscription Success Message received: " + message);
        topicMessages.computeIfAbsent("subscription_success", k -> new ArrayList<>()).add(message);
    }

    @KafkaListener(topics = "registration_success", groupId = "group_id")
    public void consumeRegistrationSuccess(String message) {
        System.out.println("Registration Success Message received: " + message);
        topicMessages.computeIfAbsent("registration_success", k -> new ArrayList<>()).add(message);
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
