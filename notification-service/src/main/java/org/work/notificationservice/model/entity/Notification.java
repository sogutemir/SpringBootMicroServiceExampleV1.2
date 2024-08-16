package org.work.notificationservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Foreign key to User

    @NotNull(message = "Order ID cannot be null")
    private Long orderId;  // Foreign key to Order

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getters and Setters
}
