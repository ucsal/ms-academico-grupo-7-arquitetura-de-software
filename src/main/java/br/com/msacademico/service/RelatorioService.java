package br.com.msacademico.service;

import br.com.msacademico.client.PessoasClient;
import br.com.msacademico.client.TurmasClient;
import br.com.msacademico.dto.AlunoResponse;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.dto.EspacoFisicoResponse;
import br.com.msacademico.dto.ProfessorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final EscolaService escolaService;
    private final DisciplinaService disciplinaService;
    private final PessoasClient pessoasClient;
    private final TurmasClient turmasClient;

    public List<EscolaResponse> relatorioEscolas() {
        return escolaService.listarTodas();
    }

    public List<DisciplinaResponse> relatorioDisciplinas() {
        return disciplinaService.listarTodas();
    }

    public List<AlunoResponse> relatorioAlunos() {
        return pessoasClient.listarAlunos();
    }

    public List<ProfessorResponse> relatorioProfessores() {
        return pessoasClient.listarProfessores();
    }

    public List<EspacoFisicoResponse> relatorioEspacosFisicos() {
        return turmasClient.listarEspacos();
    }
}