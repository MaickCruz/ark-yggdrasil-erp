package br.com.yggdrasil.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponseDTO {

    private final LocalDateTime timestamp;
    private final int status;
    private final String message;
    private final Map<String, String> errors;

    public ErrorResponseDTO(int status, String message) {
        this(status, message, null);
    }

    public ErrorResponseDTO(
            int status,
            String message,
            Map<String, String> errors) {

        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}