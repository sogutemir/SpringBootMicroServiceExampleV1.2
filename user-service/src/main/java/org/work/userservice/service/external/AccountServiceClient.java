package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.userservice.exception.MicroserviceCommunicationException;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final RestClient restClient;
    private static final Logger log = Logger.getLogger(AccountServiceClient.class.getName());

    public AccountServiceExternalAccountDto getAccountById(Long id) {
        String url = "http://account-service:8080/api/accounts/" + id;
        log.info("Fetching account for id: " + id + " from URL: " + url);
        try {
            AccountServiceExternalAccountDto account = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(AccountServiceExternalAccountDto.class);
            log.info("Successfully fetched account for id: " + id);
            return account;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching account for id: " + id, e);
            throw new MicroserviceCommunicationException("Failed to communicate with Account Service", e);
        }
    }

}