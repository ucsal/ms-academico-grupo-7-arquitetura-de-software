package br.com.msacademico.dto;

public record DisciplinaResponse(
        Long id,
        String nome,
        Integer cargaHoraria,
        MatrizResponse matriz
) {
}
