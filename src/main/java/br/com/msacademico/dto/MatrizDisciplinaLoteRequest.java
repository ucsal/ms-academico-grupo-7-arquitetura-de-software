package br.com.msacademico.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record MatrizDisciplinaLoteRequest(
        @NotNull(message = "O id da matriz e obrigatorio.")
        @Positive(message = "O id da matriz deve ser maior que zero.")
        Long matrizId,

        @NotEmpty(message = "A lista de disciplinas e obrigatoria.")
        List<@NotNull(message = "O id da disciplina e obrigatorio.")
                @Positive(message = "O id da disciplina deve ser maior que zero.") Long> disciplinaIds
) {
}
