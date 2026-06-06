package br.com.msacademico.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AlunoResponse(
        Long id,
        @JsonAlias("nome") String nomeCompleto,
        String email,
        String matricula,
        String telefone,
        Long userId,
        Long matrizId,
        String status) {
}
