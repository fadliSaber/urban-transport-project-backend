package com.urbantransport.messaging_service.events;



public class RegistrationSuccessEvent extends Event {
    private String userId;
    private String registrationId;
    private String registrationDate;

    public RegistrationSuccessEvent() {
    }

    // Constructeur paramétré
    public RegistrationSuccessEvent(String userId, String registrationId, String registrationDate) {
        this.userId = userId;
        this.registrationId = registrationId;
        this.registrationDate = registrationDate;
    }

    // Getters et Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "RegistrationSuccessEvent{" +
                "userId='" + userId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", registrationDate='" + registrationDate + '\'' +
                '}';
    }
}

