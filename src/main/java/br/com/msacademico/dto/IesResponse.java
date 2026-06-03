package br.com.msacademico.dto;

import lombok.Builder;

@Builder
public record IesResponse(
        Long id,
        String nome,
        String codigo
) {
}
