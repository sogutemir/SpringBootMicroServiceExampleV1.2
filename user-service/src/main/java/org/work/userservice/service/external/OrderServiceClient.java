package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.userservice.exception.MicroserviceCommunicationException;
import org.work.userservice.model.external.OrderServiceExternalOrderDto;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class OrderServiceClient {

    private final RestClient restClient;
    private static final Logger log = Logger.getLogger(OrderServiceClient.class.getName());

    public List<OrderServiceExternalOrderDto> getOrdersByUserId(Long userId) {
        String url = "http://order-service:8080/api/orders/user/" + userId;
        log.info("Fetching orders for userId: " + userId + " from URL: " + url);
        try {
            List<OrderServiceExternalOrderDto> orders = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("Successfully fetched orders for userId: " + userId);
            return orders;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching orders for userId: " + userId, e);
            throw new MicroserviceCommunicationException("Failed to communicate with Order Service", e);
        }
    }

}