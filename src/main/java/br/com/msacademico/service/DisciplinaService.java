package br.com.msacademico.service;

import br.com.msacademico.client.PessoasClient;
import br.com.msacademico.dto.CursoResponse;
import br.com.msacademico.dto.DisciplinaRequest;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.dto.MatrizResponse;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Curso;
import br.com.msacademico.model.Disciplina;
import br.com.msacademico.model.Escola;
import br.com.msacademico.model.Matriz;
import br.com.msacademico.repository.DisciplinaRepository;
import br.com.msacademico.repository.MatrizRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;
    private final MatrizRepository matrizRepository;
    private final PessoasClient pessoasClient;

    @Transactional
    public DisciplinaResponse criar(DisciplinaRequest request) {
        Matriz matriz = buscarMatrizPorId(request.matrizId());
        Disciplina disciplina = Disciplina.builder()
                .nome(request.nome().trim())
                .cargaHoraria(request.cargaHoraria())
                .matriz(matriz)
                .build();

        return toResponse(disciplinaRepository.save(disciplina));
    }

    @Transactional(readOnly = true)
    public Page<DisciplinaResponse> listar(Pageable pageable) {
        return disciplinaRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public DisciplinaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional(readOnly = true)
    public List<DisciplinaResponse> listarDisponiveis(Long alunoId) {
        Long matrizId = pessoasClient.buscarAlunoPorId(alunoId).matrizId();
        return disciplinaRepository.findByMatrizId(matrizId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void excluir(Long id) {
        Disciplina disciplina = buscarEntidadePorId(id);
        disciplinaRepository.delete(disciplina);
    }

    private Disciplina buscarEntidadePorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina nao encontrada com id: " + id));
    }

    private Matriz buscarMatrizPorId(Long id) {
        return matrizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matriz nao encontrada com id: " + id));
    }

    private DisciplinaResponse toResponse(Disciplina disciplina) {
        Matriz matriz = disciplina.getMatriz();
        Curso curso = matriz.getCurso();
        Escola escola = curso.getEscola();
        EscolaResponse escolaResponse = new EscolaResponse(escola.getId(), escola.getNome());
        CursoResponse cursoResponse = new CursoResponse(curso.getId(), curso.getNome(), escolaResponse);
        MatrizResponse matrizResponse = new MatrizResponse(matriz.getId(), matriz.getCodigo(), cursoResponse);
        return new DisciplinaResponse(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getCargaHoraria(),
                matrizResponse
        );
    }
}
