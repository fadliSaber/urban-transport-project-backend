package com.urbantransport.notification_service.event;


public class DelayNotificationEvent extends Event {
    private String busId;
    private String newEta;
    public DelayNotificationEvent(String busId, String newEta, String delayReason) {
        this.busId = busId;
        this.newEta = newEta;
    }

    // Getters et Setters
    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getNewEta() {
        return newEta;
    }

    public void setNewEta(String newEta) {
        this.newEta = newEta;
    }


    @Override
    public String toString() {
        return "DelayNotificationEvent{" +
                "busId='" + busId + '\'' +
                ", newEta='" + newEta + '\'' +
                '}';
    }
}

