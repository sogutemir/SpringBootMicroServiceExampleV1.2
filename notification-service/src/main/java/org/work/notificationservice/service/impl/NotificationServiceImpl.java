package org.work.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.notificationservice.exception.ResourceNotFoundException;
import org.work.notificationservice.model.dto.NotificationDto;
import org.work.notificationservice.model.entity.Notification;
import org.work.notificationservice.model.mapper.NotificationMapper;
import org.work.notificationservice.repository.NotificationRepository;
import org.work.notificationservice.service.NotificationService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper = new NotificationMapper();

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        Notification notification = notificationMapper.convertToEntity(notificationDto);
        Notification savedNotification = notificationRepository.save(notification);
        return notificationMapper.convertToDto(savedNotification);
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return notificationMapper.convertToDto(notification);
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        return notificationRepository.findAll().stream()
                .map(notificationMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        Notification existingNotification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        Notification updatedNotification = notificationMapper.convertToEntity(notificationDto);
        updatedNotification.setId(existingNotification.getId());
        Notification savedNotification = notificationRepository.save(updatedNotification);
        return notificationMapper.convertToDto(savedNotification);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notificationRepository.delete(notification);
    }
}
