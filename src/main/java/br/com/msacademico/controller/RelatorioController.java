package br.com.msacademico.controller;

import br.com.msacademico.dto.AlunoResponse;
import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.dto.EspacoFisicoResponse;
import br.com.msacademico.dto.ProfessorResponse;
import br.com.msacademico.service.RelatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
public class RelatorioController {

    private final RelatorioService relatorioService;

    @GetMapping("/escolas")
    public ResponseEntity<ApiResponse<List<EscolaResponse>>> escolas() {
        return ResponseEntity.ok(ApiResponse.of("Relatorio de escolas.", relatorioService.relatorioEscolas()));
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<ApiResponse<List<DisciplinaResponse>>> disciplinas() {
        return ResponseEntity.ok(ApiResponse.of("Relatorio de disciplinas.", relatorioService.relatorioDisciplinas()));
    }

    @GetMapping("/alunos")
    public ResponseEntity<ApiResponse<List<AlunoResponse>>> alunos() {
        return ResponseEntity.ok(ApiResponse.of("Relatorio de alunos.", relatorioService.relatorioAlunos()));
    }

    @GetMapping("/professores")
    public ResponseEntity<ApiResponse<List<ProfessorResponse>>> professores() {
        return ResponseEntity.ok(ApiResponse.of("Relatorio de professores.", relatorioService.relatorioProfessores()));
    }

    @GetMapping("/espacos-fisicos")
    public ResponseEntity<ApiResponse<List<EspacoFisicoResponse>>> espacosFisicos() {
        return ResponseEntity
                .ok(ApiResponse.of("Relatorio de espacos fisicos.", relatorioService.relatorioEspacosFisicos()));
    }
}