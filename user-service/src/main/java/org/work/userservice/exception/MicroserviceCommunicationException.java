package org.work.userservice.exception;

public class MicroserviceCommunicationException extends RuntimeException {
    public MicroserviceCommunicationException(String message) {
        super(message);
    }

    public MicroserviceCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}