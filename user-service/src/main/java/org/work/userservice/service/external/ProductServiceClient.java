package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.userservice.exception.MicroserviceCommunicationException;
import org.work.userservice.model.external.ProductServiceExternalProductDto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductServiceClient {

    private final RestClient restClient;
    private static final Logger log = Logger.getLogger(ProductServiceClient.class.getName());

    public List<ProductServiceExternalProductDto> getProductsByUserId(Long userId) {
        String url = "http://product-service:8080/api/products/user/" + userId;
        log.info("Fetching products for userId: " + userId + " from URL: " + url);
        try {
            List<ProductServiceExternalProductDto> products = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("Successfully fetched products for userId: " + userId);
            return products;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching products for userId: " + userId, e);
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByCategory(String category) {
        String url = "http://product-service:8080/api/products/category/" + category;
        log.info("Fetching products for category: " + category + " from URL: " + url);
        try {
            List<ProductServiceExternalProductDto> products = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("Successfully fetched products for category: " + category);
            return products;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching products for category: " + category, e);
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }

    public List<ProductServiceExternalProductDto> getProductsByPriceRange(Double minPrice, Double maxPrice) {
        String url = "http://product-service:8080/api/products/price-range?min=" + minPrice + "&max=" + maxPrice;
        log.info("Fetching products for price range: " + minPrice + " - " + maxPrice + " from URL: " + url);
        try {
            List<ProductServiceExternalProductDto> products = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("Successfully fetched products for price range: " + minPrice + " - " + maxPrice);
            return products;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching products for price range: " + minPrice + " - " + maxPrice, e);
            throw new MicroserviceCommunicationException("Failed to communicate with Product Service", e);
        }
    }
}