package br.com.msacademico.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ProfessorResponse(
        Long id,
        @JsonAlias("nome") String nomeCompleto,
        String email,
        String matricula,
        String telefone,
        Long escolaId,
        String status) {
}
