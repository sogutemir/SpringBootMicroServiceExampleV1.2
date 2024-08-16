package org.work.productservice.model.external;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderServiceExternalOrderDto {

    private Long id;
    private String orderNumber;
    private String orderStatus;
    private LocalDateTime orderDate;
    private Long userId;
    private Long productId;
    private List<Long> notificationIds;
    private String shippingAddress;
    private String billingAddress;
    private Double totalPrice;
    private LocalDateTime createdAt;

}
