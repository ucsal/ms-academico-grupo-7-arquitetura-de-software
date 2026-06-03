package br.com.msacademico.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MatrizDisciplinaRequest(
        @NotNull(message = "O id da matriz e obrigatorio.")
        @Positive(message = "O id da matriz deve ser maior que zero.")
        Long matrizId,

        @NotNull(message = "O id da disciplina e obrigatorio.")
        @Positive(message = "O id da disciplina deve ser maior que zero.")
        Long disciplinaId
) {
}
