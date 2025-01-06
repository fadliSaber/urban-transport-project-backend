package com.urbantransport.notification_service.event;

public abstract class Event {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}