package org.work.userservice.model.external;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductServiceExternalOrderDto {

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
