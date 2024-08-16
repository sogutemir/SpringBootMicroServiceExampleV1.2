package org.work.userservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.work.userservice.model.external.AccountServiceExternalAccountDto;

@Service
@RequiredArgsConstructor
public class AccountServiceClient {

    private final RestClient restClient;

    public AccountServiceExternalAccountDto getAccountById(Long id) {
        String url = "http://account-service:8080/api/accounts/" + id;
        return restClient.get()
                .uri(url)
                .retrieve()
                .body(AccountServiceExternalAccountDto.class);
    }
}