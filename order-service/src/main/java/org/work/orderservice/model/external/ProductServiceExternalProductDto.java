package org.work.orderservice.model.external;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductServiceExternalProductDto {
    private Long id;
    private String productName;
    private String description;
    private Double price;
    private String category;
    private Integer stockQuantity;
    private Long userId;
    private Long orderId;
    private List<Long> notificationIds;
    private LocalDateTime createdAt;
}
