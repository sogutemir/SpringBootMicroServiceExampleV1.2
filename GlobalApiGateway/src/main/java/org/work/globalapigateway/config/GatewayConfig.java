package org.work.globalapigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r.path("/account/**")
                        .uri("http://account-service:8080"))
                .route("user-service", r -> r.path("/user/**")
                        .uri("http://user-service:8080"))
                .route("order-service", r -> r.path("/order/**")
                        .uri("http://order-service:8080"))
                .route("product-service", r -> r.path("/product/**")
                        .uri("http://product-service:8080"))
                .route("notification-service", r -> r.path("/notification/**")
                        .uri("http://notification-service:8080"))
                .build();
    }
}
