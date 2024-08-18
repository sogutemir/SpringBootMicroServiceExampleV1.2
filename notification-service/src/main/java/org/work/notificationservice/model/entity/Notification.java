package org.work.notificationservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotBlank(message = "Notification type cannot be blank")
    private String notificationType;

    @NotNull(message = "Read status cannot be null")
    private Boolean isRead;

    @NotNull(message = "Sent at time cannot be null")
    private LocalDateTime sentAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    private Long userId;
    private Long orderId;
    private Long productId;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
