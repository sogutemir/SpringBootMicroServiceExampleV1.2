package org.work.accountservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO for {@link org.work.accountservice.model.entity.Account}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    @Size(message = "Account name must be less than 100 characters", max = 100)
    @NotBlank(message = "Account name cannot be blank")
    private String accountName;
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @NotBlank(message = "Address cannot be blank")
    private String address;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    private LocalDateTime createdAt;
}