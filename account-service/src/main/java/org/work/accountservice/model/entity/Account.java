package org.work.accountservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account name cannot be blank")
    @Size(max = 100, message = "Account name must be less than 100 characters")
    private String accountName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Foreign key to User

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
