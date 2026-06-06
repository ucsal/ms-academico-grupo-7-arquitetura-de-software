package br.com.msacademico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DisciplinaRequest(
                @NotBlank(message = "A sigla da disciplina e obrigatoria.") @Size(max = 50) String sigla,

                @NotBlank(message = "A descricao da disciplina e obrigatoria.") @Size(max = 500) String descricao,

                @NotNull(message = "A carga horaria da disciplina e obrigatoria.") @Positive(message = "A carga horaria deve ser maior que zero.") Integer cargaHoraria,

                Long escolaId) {
}
