package br.com.msacademico.service;

import br.com.msacademico.client.PessoasClient;
import br.com.msacademico.dto.CursoResponse;
import br.com.msacademico.dto.DisciplinaRequest;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.dto.MatrizResponse;
import br.com.msacademico.dto.ProfessorResponse;
import br.com.msacademico.exception.BusinessException;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Curso;
import br.com.msacademico.model.Disciplina;
import br.com.msacademico.model.DisciplinaProfessor;
import br.com.msacademico.model.Escola;
import br.com.msacademico.model.Matriz;
import br.com.msacademico.repository.DisciplinaProfessorRepository;
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
    private final DisciplinaProfessorRepository disciplinaProfessorRepository;
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
    public List<DisciplinaResponse> listarTodas() {
        return disciplinaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
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
    public ProfessorResponse vincularProfessor(Long disciplinaId, Long professorId) {
        Disciplina disciplina = buscarEntidadePorId(disciplinaId);
        ProfessorResponse professor = pessoasClient.buscarProfessorPorId(professorId);

        if (disciplinaProfessorRepository.existsByDisciplinaIdAndProfessorId(disciplinaId, professorId)) {
            throw new BusinessException("Professor ja vinculado a disciplina informada.");
        }

        DisciplinaProfessor disciplinaProfessor = DisciplinaProfessor.builder()
                .disciplina(disciplina)
                .professorId(professor.id())
                .build();
        disciplinaProfessorRepository.save(disciplinaProfessor);

        return professor;
    }

    @Transactional
    public void desvincularProfessor(Long disciplinaId, Long professorId) {
        buscarEntidadePorId(disciplinaId);
        DisciplinaProfessor disciplinaProfessor = disciplinaProfessorRepository
                .findByDisciplinaIdAndProfessorId(disciplinaId, professorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vinculo entre disciplina e professor nao encontrado."
                ));

        disciplinaProfessorRepository.delete(disciplinaProfessor);
    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> listarProfessores(Long disciplinaId) {
        buscarEntidadePorId(disciplinaId);
        return disciplinaProfessorRepository.findByDisciplinaId(disciplinaId).stream()
                .map(DisciplinaProfessor::getProfessorId)
                .map(pessoasClient::buscarProfessorPorId)
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
