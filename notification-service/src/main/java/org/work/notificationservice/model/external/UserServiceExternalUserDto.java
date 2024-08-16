package org.work.notificationservice.model.external;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserServiceExternalUserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long accountId;
    private LocalDateTime createdAt;
    private List<Long> orderIds;
    private List<Long> notificationIds;
    private List<Long> productIds;
}