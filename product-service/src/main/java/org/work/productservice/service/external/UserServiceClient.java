package org.work.productservice.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.work.productservice.exception.MicroserviceCommunicationException;
import org.work.productservice.model.external.UserServiceExternalUserDto;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class UserServiceClient {

    private final RestClient restClient;
    private static final Logger log = Logger.getLogger(UserServiceClient.class.getName());

    public UserServiceExternalUserDto getUsersById(Long userId){
        String url = "http://user-service:8080/api/users/" + userId;
        log.info("Fetching user for userId: " + userId + " from URL: " + url);
        try {
            UserServiceExternalUserDto user = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});
            log.info("Successfully fetched user for userId: " + userId);
            return user;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error fetching user for userId: " + userId, e);
            throw new MicroserviceCommunicationException("Failed to communicate with User Service", e);
        }
    }
    //update user by id
    public UserServiceExternalUserDto updateUserById(Long userId, UserServiceExternalUserDto userDto){
        String url = "http://user-service:8080/api/users/" + userId;
        log.info("Updating user for userId: " + userId + " from URL: " + url);
        try {
            UserServiceExternalUserDto updatedUser = restClient.put()
                    .uri(url)
                    .body(userDto)
                    .retrieve()
                    .body(UserServiceExternalUserDto.class); 
            log.info("Successfully updated user for userId: " + userId);
            return updatedUser;
        } catch (RestClientException e) {
            log.log(Level.SEVERE, "Error updating user for userId: " + userId, e);
            throw new MicroserviceCommunicationException("Failed to communicate with User Service", e);
        }
    }




}
