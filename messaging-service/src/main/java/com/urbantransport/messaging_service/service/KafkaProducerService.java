package com.urbantransport.messaging_service.service;

import com.urbantransport.messaging_service.events.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    private static final String BUS_ARRIVAL_TOPIC = "bus_arrival";
    private static final String TICKET_PURCHASE_TOPIC = "ticket_purchase";
    private static final String ROUTE_CHANGE_TOPIC = "route_change";
    private static final String DELAY_NOTIFICATION_TOPIC = "delay_notification";
    private static final String SUBSCRIPTION_SUCCESS_TOPIC = "subscription_success";
    private static final String REGISTRATION_SUCCESS_TOPIC = "registration_success";

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }



    private void sendMessage(String topic, Object message) {
        try {
            kafkaTemplate.send(topic, message);
            logger.info("Sent message=[{}] to topic=[{}]", message, topic);
        } catch (Exception e) {
            logger.error("Error sending message to Kafka topic {}: {}", topic, e.getMessage());
        }
    }



    public void sendBusArrivalMessage(BusArrivalEvent event) {
        sendMessage(BUS_ARRIVAL_TOPIC, event);
    }

    public void sendTicketPurchaseMessage(TicketPurchaseEvent event) {
        sendMessage(TICKET_PURCHASE_TOPIC, event);
    }

    public void sendRouteChangeMessage(RouteChangeEvent event) {
        sendMessage(ROUTE_CHANGE_TOPIC, event);
    }

    public void sendDelayNotificationMessage(DelayNotificationEvent event) {
        sendMessage(DELAY_NOTIFICATION_TOPIC, event);
    }

    public void sendSubscriptionSuccessMessage(SubscriptionSuccessEvent event) {
        sendMessage(SUBSCRIPTION_SUCCESS_TOPIC, event);
    }

    public void sendRegistrationSuccessMessage(RegistrationSuccessEvent event) {
        sendMessage(REGISTRATION_SUCCESS_TOPIC, event);
    }
}