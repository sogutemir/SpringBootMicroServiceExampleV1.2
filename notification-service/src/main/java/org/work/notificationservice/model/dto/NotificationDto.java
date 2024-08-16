package org.work.notificationservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.work.notificationservice.model.entity.Notification}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    private Long id;
    @NotBlank(message = "Message cannot be blank")
    private String message;
    @NotBlank(message = "Notification type cannot be blank")
    private String notificationType;
    @NotNull(message = "Read status cannot be null")
    private Boolean isRead;
    @NotNull(message = "Sent at time cannot be null")
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
    private Long userId;
    private Long orderId;
    private Long productId;
}