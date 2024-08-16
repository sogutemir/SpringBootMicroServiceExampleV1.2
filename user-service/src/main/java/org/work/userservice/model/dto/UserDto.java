package org.work.userservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link org.work.userservice.model.entity.User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private Long id;
    @Size(message = "Username must be less than 50 characters", max = 50)
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @Size(message = "Password must be at least 8 characters long", min = 8)
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    private Long accountId;
    private LocalDateTime createdAt;
    private List<Long> orderIds;
    private List<Long> notificationIds;
    private List<Long> productIds;
}