package org.work.productservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(max = 100, message = "Product name must be less than 100 characters")
    private String productName;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @NotNull(message = "Price cannot be null")
    private Double price;

    @NotBlank(message = "Category cannot be blank")
    private String category;

    @NotNull(message = "Stock quantity cannot be null")
    private Integer stockQuantity;

    @ElementCollection
    private List<Long> userId;

    private Long orderId;

    @ElementCollection
    private List<Long> notificationIds;

    private LocalDateTime createdAt;

}
