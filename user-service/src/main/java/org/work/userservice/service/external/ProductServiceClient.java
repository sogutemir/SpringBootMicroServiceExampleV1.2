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

}