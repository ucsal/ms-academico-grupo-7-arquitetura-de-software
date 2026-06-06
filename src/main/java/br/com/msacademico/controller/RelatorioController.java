package br.com.msacademico.controller;

import br.com.msacademico.dto.AlunoResponse;
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
    public ResponseEntity<List<EscolaResponse>> escolas() {
        return ResponseEntity.ok(relatorioService.relatorioEscolas());
    }

    @GetMapping("/disciplinas")
    public ResponseEntity<List<DisciplinaResponse>> disciplinas() {
        return ResponseEntity.ok(relatorioService.relatorioDisciplinas());
    }

    @GetMapping("/alunos")
    public ResponseEntity<List<AlunoResponse>> alunos() {
        return ResponseEntity.ok(relatorioService.relatorioAlunos());
    }

    @GetMapping("/professores")
    public ResponseEntity<List<ProfessorResponse>> professores() {
        return ResponseEntity.ok(relatorioService.relatorioProfessores());
    }

    @GetMapping("/espacos-fisicos")
    public ResponseEntity<List<EspacoFisicoResponse>> espacosFisicos() {
        return ResponseEntity.ok(relatorioService.relatorioEspacosFisicos());
    }
}