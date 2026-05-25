package br.com.msacademico.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        LocalDateTime timestamp,
        String message,
        T data
) {

    public static <T> ApiResponse<T> of(String message, T data) {
        return new ApiResponse<>(LocalDateTime.now(), message, data);
    }
}
