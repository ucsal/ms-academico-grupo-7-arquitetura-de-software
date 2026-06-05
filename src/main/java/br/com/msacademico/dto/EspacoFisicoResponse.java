package br.com.msacademico.dto;

public record EspacoFisicoResponse(
        Long id,
        String nome,
        Integer capacidade,
        Boolean disponivel) {
}