package br.com.msacademico.dto;

public record CursoResponse(
        Long id,
        String nome,
        EscolaResponse escola
) {
}
