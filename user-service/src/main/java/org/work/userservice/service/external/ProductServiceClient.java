package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.userservice.exception.MicroserviceCommunicationException;
import org.work.userservice.model.external.ProductServiceExternalProductDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    private final RestClient restClient;

    public ProductServiceExternalProductDto getProductById(Long productId) {
        String url = "http://product-service:8080/api/products/" + productId;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(ProductServiceExternalProductDto.class);
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByUserId(Long userId) {
        String url = "http://product-service:8080/api/products/user/" + userId;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByOrderId(Long orderId) {
        String url = "http://product-service:8080/api/products/order/" + orderId;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByNotificationId(Long notificationId) {
        String url = "http://product-service:8080/api/products/notification/" + notificationId;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByUserIdAndPriceRange(Long userId, Double minPrice, Double maxPrice) {
        String url = "http://product-service:8080/api/products/user/" + userId + "?minPrice=" + minPrice + "&maxPrice=" + maxPrice;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }
}