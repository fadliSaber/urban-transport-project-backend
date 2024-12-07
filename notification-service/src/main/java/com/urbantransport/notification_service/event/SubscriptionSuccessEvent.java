package com.urbantransport.notification_service.event;


public class SubscriptionSuccessEvent  {
    private String userId;

    private String subscriptionId;

    private String subscriptionDate;
    public SubscriptionSuccessEvent() {
    }

    // Parameterized Constructor
    public SubscriptionSuccessEvent(String userId, String subscriptionId, String subscriptionDate) {
        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.subscriptionDate = subscriptionDate;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(String subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    // toString Method
    @Override
    public String toString() {
        return "SubscriptionSuccessEvent{" +
                "userId='" + userId + '\'' +
                ", subscriptionId='" + subscriptionId + '\'' +
                ", subscriptionDate='" + subscriptionDate + '\'' +
                '}';
    }
}



