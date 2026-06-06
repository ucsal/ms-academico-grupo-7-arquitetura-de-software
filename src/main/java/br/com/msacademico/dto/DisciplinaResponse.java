package br.com.msacademico.dto;

import java.util.List;

public record DisciplinaResponse(
                Long id,
                String sigla,
                String descricao,
                Integer cargaHoraria,
                String dataCadastro,
                String status,
                Long escolaId,
                String escolaNome,
                List<MatrizResumida> matrizes) {
}
