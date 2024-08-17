package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.userservice.exception.MicroserviceCommunicationException;
import org.work.userservice.model.external.OrderServiceExternalOrderDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceClient {

    private final RestClient restClient;

    public List<OrderServiceExternalOrderDto> getOrdersByIds(List<Long> orderIds) {
        String url = "http://order-service:8080/api/orders/ids";
        try {
            return restClient.post()
                    .uri(url)
                    .body(orderIds)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Order Service", e);
        }
    }

    public List<OrderServiceExternalOrderDto> getOrdersByUserId(Long userId) {
        String url = "http://order-service:8080/api/orders/user/" + userId;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Order Service", e);
        }
    }

    public List<OrderServiceExternalOrderDto> getOrdersByUserIdAndStatus(Long userId, String status) {
        String url = "http://order-service:8080/api/orders/user/" + userId + "/status/" + status;
        try {
            return restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException e) {
            throw new MicroserviceCommunicationException("Failed to communicate with Order Service", e);
        }
    }



}