package org.work.productservice.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private Long userId;  // Foreign key to User
    private Long orderId; // Foreign key to Order

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
