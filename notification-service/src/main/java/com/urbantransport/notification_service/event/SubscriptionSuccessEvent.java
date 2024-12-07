package com.urbantransport.notification_service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SubscriptionSuccessEvent {
    private String subscriptionPlan;
    private String userId;

}

