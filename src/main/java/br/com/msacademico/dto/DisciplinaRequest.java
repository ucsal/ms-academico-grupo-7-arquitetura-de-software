package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DisciplinaRequest(
        @NotBlank(message = "O nome da disciplina e obrigatorio.")
        @Size(max = 150, message = "O nome da disciplina deve ter no maximo 150 caracteres.")
        String nome,

        @NotNull(message = "A carga horaria da disciplina e obrigatoria.")
        @Positive(message = "A carga horaria deve ser maior que zero.")
        Integer cargaHoraria,

        @NotNull(message = "O id da matriz e obrigatorio.")
        @Positive(message = "O id da matriz deve ser maior que zero.")
        Long matrizId
) {
}
