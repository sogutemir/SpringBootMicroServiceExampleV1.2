package org.work.notificationservice.service;

import org.work.notificationservice.model.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto getNotificationById(Long id);
    List<NotificationDto> getAllNotifications();
    NotificationDto updateNotification(Long id, NotificationDto notificationDto);
    void deleteNotification(Long id);
}
