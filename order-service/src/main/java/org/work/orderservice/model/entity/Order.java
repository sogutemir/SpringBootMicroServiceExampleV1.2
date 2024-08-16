package org.work.orderservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Order number cannot be blank")
    @Size(max = 50, message = "Order number must be less than 50 characters")
    private String orderNumber;

    @NotBlank(message = "Order status cannot be blank")
    private String orderStatus;

    @NotNull(message = "Order date cannot be null")
    private LocalDateTime orderDate;

    @NotNull(message = "User ID cannot be null")
    private Long userId;  // Foreign key to User

    @NotNull(message = "Product ID cannot be null")
    private Long productId;  // Foreign key to Product

    @NotBlank(message = "Shipping address cannot be blank")
    private String shippingAddress;

    @NotBlank(message = "Billing address cannot be blank")
    private String billingAddress;

    @NotNull(message = "Total price cannot be null")
    private Double totalPrice;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
