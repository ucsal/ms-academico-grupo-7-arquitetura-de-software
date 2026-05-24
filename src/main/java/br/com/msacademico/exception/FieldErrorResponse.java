package br.com.msacademico.exception;

public record FieldErrorResponse(
        String field,
        String message
) {
}
