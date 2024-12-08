package com.urbantransport.notification_service.Controller;


import com.urbantransport.notification_service.service.NotificationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }

    /**
     * Endpoint to get all in-memory messages.
     * @return List of messages.
     */
    @GetMapping("/messages")
    public ResponseEntity<List<String>> getMessages() {
        List<String> messages = notificationsService.getMessages();
        return ResponseEntity.ok(messages);
    }

    /**
     * Endpoint to clear all in-memory messages.
     */
    @DeleteMapping("/messages")
    public ResponseEntity<Void> clearMessages() {
        notificationsService.clearMessages();
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint to save a notification to Redis.
     * @param userId The user ID to associate with the notification.
     * @param message The message to save.
     */
    @PostMapping("/save")
    public ResponseEntity<Void> saveNotification(@RequestParam String userId, @RequestParam String message) {
        notificationsService.saveNotificationToRedis(userId, message);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to retrieve notifications from Redis for a user.
     * @param userId The user ID.
     * @return List of notifications.
     */
    @GetMapping("/redis/{userId}")
    public ResponseEntity<List<String>> getNotificationsFromRedis(@PathVariable String userId) {
        List<String> notifications = notificationsService.getNotificationsFromRedis(userId);
        return ResponseEntity.ok(notifications);
    }
}
