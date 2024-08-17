# SpringBootMicroServiceExampleV1.2

## Overview

This project is a microservices-based application that manages products, users, accounts, orders, and notifications. It consists of the following main services:

1. **User Service**: Manages user information and their orders.
2. **Product Service**: Manages product information and interacts with the User Service to update user-related data.
3. **Account Service**: Manages user accounts and authentication.
4. **Order Service**: Manages orders placed by users.
5. **Notification Service**: Manages notifications sent to users.

## Technologies Used

- **Java**: Programming language used for developing the services.
- **Spring Boot**: Framework used to create stand-alone, production-grade Spring-based applications.
- **Maven**: Build automation tool used for managing project dependencies.
- **SQL**: Database language used for managing relational databases.
- **Docker**: Containerization platform for packaging and deploying services.

## Project Structure

### User Service

- **Package**: `org.work.userservice`
- **Main Class**: `UserServiceApplication`
- **Key Components**:
    - `UserServiceImpl`: Service implementation for user-related operations.
    - `OrderServiceClient`: Client for interacting with the Order Service.
    - `ProductServiceClient`: Client for interacting with the Product Service.
    - `AccountServiceClient`: Client for interacting with the Account Service.
    - `UserRepository`: Repository interface for user data access.
    - `UserMapper`: Mapper for converting between `User` entities and `UserDto` data transfer objects.
  

### Product Service

- **Package**: `org.work.productservice`
- **Main Class**: `ProductServiceApplication`
- **Key Components**:
    - `ProductServiceImpl`: Service implementation for product-related operations.
    - `UserServiceClient`: Client for interacting with the User Service.
    - `ProductRepository`: Repository interface for product data access.
    - `ProductMapper`: Mapper for converting between `Product` entities and `ProductDto` data transfer objects.

### Account Service

- **Package**: `org.work.accountservice`
- **Main Class**: `AccountServiceApplication`
- **Key Components**:
    - `AccountServiceImpl`: Service implementation for account-related operations.
    - `AccountRepository`: Repository interface for account data access.
    - `UserServiceClient`: Client for interacting with the User Service.
    - `AccountMapper`: Mapper for converting between `Account` entities and `AccountDto` data transfer objects.

### Order Service

- **Package**: `org.work.orderservice`
- **Main Class**: `OrderServiceApplication`
- **Key Components**:
    - `OrderServiceImpl`: Service implementation for order-related operations.
    - `OrderRepository`: Repository interface for order data access.
    - `UserServiceClient`: Client for interacting with the User Service.
    - `OrderMapper`: Mapper for converting between `Order` entities and `OrderDto` data transfer objects.

### Notification Service

- **Package**: `org.work.notificationservice`
- **Main Class**: `NotificationServiceApplication`
- **Key Components**:
    - `NotificationServiceImpl`: Service implementation for notification-related operations.
    - `NotificationRepository`: Repository interface for notification data access.
    - `NotificationMapper`: Mapper for converting between `Notification` entities and `NotificationDto` data transfer objects.

## Endpoints

### User Service

- **Create User**
    - **URL**: `/api/users`
    - **Method**: `POST`
    - **Description**: Creates a new user.
  
- **Get User by ID**
    - **URL**: `/api/users/{id}`
    - **Method**: `GET`
    - **Description**: Fetches a user by its ID.
  
- **Get All Users**
    - **URL**: `/api/users`
    - **Method**: `GET`
    - **Description**: Fetches all users.
  
- **Update User**
    - **URL**: `/api/users/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing user by its ID.
  
- **Delete User**
  - **URL**: `/api/users/{id}`
  - **Method**: `DELETE`
  - **Description**: Deletes a user by its ID.
  
- **Get Orders by User ID**
    - **URL**: `/api/users/{userId}/orders`
    - **Method**: `GET`
    - **Description**: Fetches all orders for a given user ID.

- **Get Top 5 Most Recent Orders by User ID**
    - **URL**: `/api/users/{userId}/orders/recent`
    - **Method**: `GET`
    - **Description**: Fetches the top 5 most recent orders for a given user ID.

### Product Service

- **Create Product**
    - **URL**: `/api/products`
    - **Method**: `POST`
    - **Description**: Creates a new product.

- **Get Product by ID**
    - **URL**: `/api/products/{id}`
    - **Method**: `GET`
    - **Description**: Fetches a product by its ID.

- **Get All Products**
    - **URL**: `/api/products`
    - **Method**: `GET`
    - **Description**: Fetches all products.

- **Update Product**
    - **URL**: `/api/products/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing product by its ID.

- **Delete Product**
    - **URL**: `/api/products/{id}`
    - **Method**: `DELETE`
    - **Description**: Deletes a product by its ID.

### Account Service

- **Create Account**
    - **URL**: `/api/accounts`
    - **Method**: `POST`
    - **Description**: Creates a new account.

- **Get Account by ID**
    - **URL**: `/api/accounts/{id}`
    - **Method**: `GET`
    - **Description**: Fetches an account by its ID.

- **Update Account**
    - **URL**: `/api/accounts/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing account by its ID.

- **Delete Account**
    - **URL**: `/api/accounts/{id}`
    - **Method**: `DELETE`
    - **Description**: Deletes an account by its ID.

### Order Service

- **Create Order**
    - **URL**: `/api/orders`
    - **Method**: `POST`
    - **Description**: Creates a new order.

- **Get Order by ID**
    - **URL**: `/api/orders/{id}`
    - **Method**: `GET`
    - **Description**: Fetches an order by its ID.

- **Get All Orders**
    - **URL**: `/api/orders`
    - **Method**: `GET`
    - **Description**: Fetches all orders.

- **Update Order**
    - **URL**: `/api/orders/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing order by its ID.

- **Delete Order**
    - **URL**: `/api/orders/{id}`
    - **Method**: `DELETE`
    - **Description**: Deletes an order by its ID.

### Notification Service

- **Create Notification**
    - **URL**: `/api/notifications`
    - **Method**: `POST`
    - **Description**: Creates a new notification.

- **Get Notification by ID**
    - **URL**: `/api/notifications/{id}`
    - **Method**: `GET`
    - **Description**: Fetches a notification by its ID.

- **Get All Notifications**
    - **URL**: `/api/notifications`
    - **Method**: `GET`
    - **Description**: Fetches all notifications.

- **Update Notification**
    - **URL**: `/api/notifications/{id}`
    - **Method**: `PUT`
    - **Description**: Updates an existing notification by its ID.

- **Delete Notification**
    - **URL**: `/api/notifications/{id}`
    - **Method**: `DELETE`
    - **Description**: Deletes a notification by its ID.

## Exception Handling

- **ResourceNotFoundException**: Thrown when a requested resource is not found.
- **MicroserviceCommunicationException**: Thrown when there is an error communicating with another microservice.
- **InvalidRequestException**: Thrown when an invalid request is made.
- **GlobalExceptionHandler**: Handles exceptions globally for all controllers.

## Logging

Logging is implemented using the `java.util.logging` package. Logs are generated for key operations and exceptions to help with debugging and monitoring.

## Running the Application

1. Clone the repository: `git clone
2. Navigate to the project directory: `cd SpringBootMicroServiceExampleV1.2`
3. Build the project: `mvn clean install -DskipTests`
4. Run the services using Docker Compose: `docker-compose build / docker-compose up`
5. Access the services using the provided endpoints.
6. Stop the services: `docker-compose down`
7. Remove the services: `docker-compose rm`


