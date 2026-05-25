package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CursoRequest(
        @NotBlank(message = "O nome do curso e obrigatorio.")
        @Size(max = 150, message = "O nome do curso deve ter no maximo 150 caracteres.")
        String nome,

        @NotNull(message = "O id da escola e obrigatorio.")
        @Positive(message = "O id da escola deve ser maior que zero.")
        Long escolaId
) {
}
