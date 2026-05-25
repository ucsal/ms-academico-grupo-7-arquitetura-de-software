package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MatrizRequest(
        @NotBlank(message = "O codigo da matriz e obrigatorio.")
        @Size(max = 50, message = "O codigo da matriz deve ter no maximo 50 caracteres.")
        String codigo,

        @NotNull(message = "O id do curso e obrigatorio.")
        @Positive(message = "O id do curso deve ser maior que zero.")
        Long cursoId
) {
}
