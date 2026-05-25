package br.com.msacademico.dto;

public record MatrizResponse(
        Long id,
        String codigo,
        CursoResponse curso
) {
}
