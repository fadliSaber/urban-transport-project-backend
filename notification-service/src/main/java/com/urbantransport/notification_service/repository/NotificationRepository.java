package com.urbantransport.notification_service.repository;

import com.urbantransport.notification_service.entity.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, String> {
}