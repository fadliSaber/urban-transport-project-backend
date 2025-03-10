package com.urbantransport.messaging_service.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic busArrivalTopic() {
        return new NewTopic("bus_arrival", 1, (short) 1);
    }

    @Bean
    public NewTopic ticketPurchaseTopic() {
        return new NewTopic("ticket_purchase", 1, (short) 1);
    }

    @Bean
    public NewTopic routeChangeTopic() {
        return new NewTopic("route_change", 1, (short) 1);
    }

    @Bean
    public NewTopic delayNotificationTopic() {
        return new NewTopic("delay_notification", 1, (short) 1);
    }

    @Bean
    public NewTopic subscriptionSuccessTopic() {
        return new NewTopic("subscription_success", 1, (short) 1);
    }

    @Bean
    public NewTopic registrationSuccessTopic() {
        return new NewTopic("registration_success", 1, (short) 1);
    }
}