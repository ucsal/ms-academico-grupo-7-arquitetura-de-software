package br.com.msacademico.dto;

public record MatrizDisciplinaResponse(
        Long id,
        Long matrizId,
        String matrizCodigo,
        Long disciplinaId,
        String disciplinaNome
) {
}
