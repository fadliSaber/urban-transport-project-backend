package com.urbantransport.notification_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka.topics")
public class KafkaTopicsConfig {
    private String busArrival;
    private String ticketPurchase;
    private String routeChange;
    private String delayNotification;
    private String subscriptionSuccess;
    private String registrationSuccess;

}
