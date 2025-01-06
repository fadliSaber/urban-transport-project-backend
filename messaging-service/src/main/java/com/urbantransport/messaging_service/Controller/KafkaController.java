package com.urbantransport.messaging_service.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urbantransport.messaging_service.events.*;
import com.urbantransport.messaging_service.service.KafkaConsumerService;
import com.urbantransport.messaging_service.service.KafkaProducerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kafka")
public class KafkaController {
    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private KafkaConsumerService kafkaConsumerService;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/sendMessage/{topic}")
    public ResponseEntity<String> sendMessage(@PathVariable String topic, @RequestBody String eventJson) {
        try {
            Object event = null;

            // Deserialize the event JSON into specific event objects
            switch (topic) {
                case "bus_arrival":
                    event = objectMapper.readValue(eventJson, BusArrivalEvent.class);
                    kafkaProducerService.sendBusArrivalMessage((BusArrivalEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic bus_arrival");
                case "ticket_purchase":
                    event = objectMapper.readValue(eventJson, TicketPurchaseEvent.class);
                    kafkaProducerService.sendTicketPurchaseMessage((TicketPurchaseEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic ticket_purchase");
                case "route_change":
                    event = objectMapper.readValue(eventJson, RouteChangeEvent.class);
                    kafkaProducerService.sendRouteChangeMessage((RouteChangeEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic route_change");
                case "delay_notification":
                    event = objectMapper.readValue(eventJson, DelayNotificationEvent.class);
                    kafkaProducerService.sendDelayNotificationMessage((DelayNotificationEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic delay_notification");
                case "subscription_success":
                    event = objectMapper.readValue(eventJson, SubscriptionSuccessEvent.class);
                    kafkaProducerService.sendSubscriptionSuccessMessage((SubscriptionSuccessEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic subscription_success");
                case "registration_success":
                    event = objectMapper.readValue(eventJson, RegistrationSuccessEvent.class);
                    kafkaProducerService.sendRegistrationSuccessMessage((RegistrationSuccessEvent) event);
                    return ResponseEntity.ok("Message sent to Kafka topic registration_success");
                default:
                    return ResponseEntity.badRequest().body("Invalid topic");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/messages/{topic}")
    public ResponseEntity<List<Object>> getMessagesForTopic(@PathVariable String topic) {
        // Retrieve messages as List<Object> from the consumer service
        List<Object> messages = kafkaConsumerService.getMessagesForTopic(topic);

        if (messages == null || messages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/messages")
    public ResponseEntity<Map<String, List<Object>>> getAllTopicMessages() {
        // Retrieve all messages as Map<String, List<Object>> from the consumer service
        Map<String, List<Object>> allMessages = kafkaConsumerService.getAllTopicMessages();

        if (allMessages == null || allMessages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allMessages);
    }
}
