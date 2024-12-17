package com.urbantransport.notification_service.event;


public class SubscriptionSuccessEvent extends Event {

    private String subscriptionId;

    private String subscriptionDate;
    public SubscriptionSuccessEvent() {
    }

    // Parameterized Constructor
    public SubscriptionSuccessEvent(String userId, String subscriptionId, String subscriptionDate) {
        this.subscriptionId = subscriptionId;
        this.subscriptionDate = subscriptionDate;
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

}



