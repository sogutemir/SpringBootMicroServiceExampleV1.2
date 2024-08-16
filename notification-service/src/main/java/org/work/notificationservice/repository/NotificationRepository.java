package org.work.notificationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.work.notificationservice.model.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
