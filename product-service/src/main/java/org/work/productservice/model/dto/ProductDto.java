package org.work.productservice.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link org.work.productservice.model.entity.Product}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @Size(message = "Product name must be less than 100 characters", max = 100)
    @NotBlank(message = "Product name cannot be blank")
    private String productName;

    @Size(message = "Description must be less than 255 characters", max = 255)
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Stock quantity cannot be null")
    private Integer stockQuantity;

    private Long userId;

    private Long orderId;

    private List<Long> notificationIds;

    private LocalDateTime createdAt;
}
