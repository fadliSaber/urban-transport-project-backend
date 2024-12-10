package com.urbantransport.notification_service.event;


public class TicketPurchaseEvent extends Event{
    private String userId;
    private String ticketId;
    private String purchaseTime;

    public TicketPurchaseEvent() {
    }

    // Constructeur paramétré
    public TicketPurchaseEvent(String userId, String ticketId, String purchaseTime) {
        this.userId = userId;
        this.ticketId = ticketId;
        this.purchaseTime = purchaseTime;
    }

    // Getters et Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    @Override
    public String toString() {
        return "TicketPurchaseEvent{" +
                "userId='" + userId + '\'' +
                ", ticketId='" + ticketId + '\'' +
                ", purchaseTime='" + purchaseTime + '\'' +
                '}';
    }
}