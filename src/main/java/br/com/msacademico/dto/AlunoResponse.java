package br.com.msacademico.dto;

public record AlunoResponse(
                Long id,
                String nome,
                String email,
                String matricula,
                Long matrizId) {
}