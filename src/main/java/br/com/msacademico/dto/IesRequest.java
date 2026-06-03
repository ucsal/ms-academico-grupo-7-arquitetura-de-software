package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record IesRequest(
        @NotBlank(message = "O nome da IES e obrigatorio.")
        @Size(max = 100, message = "O nome da IES deve ter no maximo 100 caracteres.")
        String nome,

        @NotBlank(message = "O codigo da IES e obrigatorio.")
        @Size(max = 20, message = "O codigo da IES deve ter no maximo 20 caracteres.")
        String codigo
) {
}
