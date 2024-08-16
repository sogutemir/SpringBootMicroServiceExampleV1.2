package org.work.orderservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link org.work.orderservice.model.entity.Order}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto{
    private Long id;
    @Size(message = "Order number must be less than 50 characters", max = 50)
    @NotBlank(message = "Order number cannot be blank")
    private String orderNumber;
    @NotBlank(message = "Order status cannot be blank")
    private String orderStatus;
    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;
    @NotNull(message = "User ID cannot be null")
    private Long userId;
    private Long productId;
    private List<Long> notificationIds;
    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;
    @NotBlank(message = "Billing address cannot be blank")
    private String billingAddress;
    @NotNull(message = "Total price cannot be null")
    private Double totalPrice;
    private LocalDateTime createdAt;
}