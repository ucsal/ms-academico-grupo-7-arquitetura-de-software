package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EscolaRequest(
        @NotBlank(message = "O nome da escola e obrigatorio.")
        @Size(max = 150, message = "O nome da escola deve ter no maximo 150 caracteres.")
        String nome
) {
}
