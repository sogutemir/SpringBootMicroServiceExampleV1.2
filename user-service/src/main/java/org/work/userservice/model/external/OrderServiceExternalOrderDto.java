package org.work.userservice.model.external;

import java.time.LocalDateTime;
import java.util.List;

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
