package org.work.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.work.notificationservice.exception.ResourceNotFoundException;
import org.work.notificationservice.model.dto.NotificationDto;
import org.work.notificationservice.model.entity.Notification;
import org.work.notificationservice.model.mapper.NotificationMapper;
import org.work.notificationservice.repository.NotificationRepository;
import org.work.notificationservice.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper = new NotificationMapper();
    private static final Logger log = Logger.getLogger(NotificationServiceImpl.class.getName());
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final String ORDER_TOPIC = "ORDER_TOPIC";

    @Override
    public NotificationDto createNotification(NotificationDto notificationDto) {
        try {
            Notification notification = notificationMapper.convertToEntity(notificationDto);
            Notification savedNotification = notificationRepository.save(notification);
            log.info("Notification created successfully with id: " + savedNotification.getId());
            return notificationMapper.convertToDto(savedNotification);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating notification", e);
            throw e;
        }
    }


    @Override
    public NotificationDto getNotificationById(Long id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
            log.info("Notification fetched successfully with id: " + id);
            return notificationMapper.convertToDto(notification);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Notification not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching notification with id: " + id, e);
            throw e;
        }
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        try {
            List<NotificationDto> notifications = notificationRepository.findAll().stream()
                    .map(notificationMapper::convertToDto)
                    .collect(Collectors.toList());
            log.info("All notifications fetched successfully");
            return notifications;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error fetching all notifications", e);
            throw e;
        }
    }

    @Override
    public NotificationDto updateNotification(Long id, NotificationDto notificationDto) {
        try {
            Notification existingNotification = notificationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
            Notification updatedNotification = notificationMapper.convertToEntity(notificationDto);
            updatedNotification.setId(existingNotification.getId());
            Notification savedNotification = notificationRepository.save(updatedNotification);
            log.info("Notification updated successfully with id: " + id);
            return notificationMapper.convertToDto(savedNotification);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Notification not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error updating notification with id: " + id, e);
            throw e;
        }
    }

    @Override
    public void deleteNotification(Long id) {
        try {
            Notification notification = notificationRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
            notificationRepository.delete(notification);
            log.info("Notification deleted successfully with id: " + id);
        } catch (ResourceNotFoundException e) {
            log.log(Level.SEVERE, "Notification not found with id: " + id, e);
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error deleting notification with id: " + id, e);
            throw e;
        }
    }

    @KafkaListener(topics = ORDER_TOPIC, groupId = "notification_group")
    private void listenOrderTopic(String message, Acknowledgment acknowledgment) {
        log.info("Received message from order topic: " + message);
        try {
            Long orderId = Long.parseLong(message.replace("\"", ""));
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setMessage("Order created with id: " + orderId);
            notificationDto.setNotificationType("Order");
            notificationDto.setIsRead(false);
            notificationDto.setSentAt(LocalDateTime.now());

            notificationDto.setOrderId(orderId);
            createNotification(notificationDto);

        } catch (NumberFormatException e) {
            log.log(Level.SEVERE, "Error parsing order ID from message: " + message, e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error creating notification for message: " + message, e);
        }
    }

}