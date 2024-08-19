package org.work.globalapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r.path("/api/accounts/**")
                        .uri("http://account-service:8080"))
                .route("user-service", r -> r.path("/api/users/**")
                        .uri("http://user-service:8080"))
                .route("order-service", r -> r.path("/api/orders/**")
                        .uri("http://order-service:8080"))
                .route("product-service", r -> r.path("/api/products/**")
                        .uri("http://product-service:8080"))
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .uri("http://notification-service:8080"))
                .build();
    }
}