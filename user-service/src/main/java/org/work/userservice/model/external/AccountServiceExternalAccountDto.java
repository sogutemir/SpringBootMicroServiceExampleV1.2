package org.work.userservice.model.external;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountServiceExternalAccountDto {

    private Long id;
    private String accountName;
    private String email;
    private String phoneNumber;
    private String address;
    private Long userId;
    private LocalDateTime createdAt;

}
