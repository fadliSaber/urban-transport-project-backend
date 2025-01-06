package com.urbantransport.notification_service.Controller;

import com.urbantransport.notification_service.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    @Autowired
    public NotificationsController(NotificationsService notificationsService) {
        this.notificationsService = notificationsService;
    }
    @GetMapping("/{userId}")
    public List<Object> getNotificationsForUser(@PathVariable String userId) {
        return notificationsService.getNotificationsForUser(userId);
    }

    @GetMapping("/messages")
    public ResponseEntity<Map<String, List<Object>>> getAllTopicMessages() {
        Map<String, List<Object>> allMessages = notificationsService.getAllTopicMessages();

        if (allMessages == null || allMessages.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allMessages);
    }
}
