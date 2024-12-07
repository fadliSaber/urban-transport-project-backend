package com.urbantransport.notification_service.Controller;


import com.urbantransport.notification_service.service.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationsService notificationsService;
    @GetMapping("/messages")
    public List<String> getMessages() {
        return notificationsService.getMessages();
    }


}
