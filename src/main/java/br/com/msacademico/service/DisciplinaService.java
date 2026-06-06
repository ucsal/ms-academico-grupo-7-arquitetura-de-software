package br.com.msacademico.service;

import br.com.msacademico.client.PessoasClient;
import br.com.msacademico.dto.AlunoResponse;
import br.com.msacademico.dto.DisciplinaRequest;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.dto.MatrizResumida;
import br.com.msacademico.dto.ProfessorResponse;
import br.com.msacademico.exception.BusinessException;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Disciplina;
import br.com.msacademico.model.DisciplinaProfessor;
import br.com.msacademico.model.Escola;
import br.com.msacademico.repository.DisciplinaProfessorRepository;
import br.com.msacademico.repository.DisciplinaRepository;
import br.com.msacademico.repository.EscolaRepository;
import br.com.msacademico.repository.MatrizDisciplinaRepository;
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
    private final MatrizDisciplinaRepository matrizDisciplinaRepository;
    private final DisciplinaProfessorRepository disciplinaProfessorRepository;
    private final EscolaRepository escolaRepository;
    private final PessoasClient pessoasClient;

    @Transactional
    public DisciplinaResponse criar(DisciplinaRequest request) {
        Disciplina disciplina = Disciplina.builder()
                .sigla(request.sigla().trim())
                .descricao(request.descricao().trim())
                .nome(request.sigla().trim())
                .cargaHoraria(request.cargaHoraria())
                .escolaId(request.escolaId())
                .dataCadastro(java.time.LocalDateTime.now().toString())
                .status("ATIVO")
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
        AlunoResponse aluno;
        try {
            aluno = (alunoId != null)
                    ? pessoasClient.buscarAlunoPorId(alunoId)
                    : pessoasClient.buscarAlunoAtual();
        } catch (Exception e) {
            return List.of();
        }

        if (aluno.matrizId() == null) {
            return List.of();
        }

        return matrizRepository.findById(aluno.matrizId())
                .map(matriz -> matriz.getDisciplinas().stream()
                        .map(this::toResponse)
                        .toList())
                .orElse(List.of());
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
                        "Vinculo entre disciplina e professor nao encontrado."));

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
    public DisciplinaResponse atualizar(Long id, DisciplinaRequest request) {
        Disciplina disciplina = buscarEntidadePorId(id);
        disciplina.setSigla(request.sigla().trim());
        disciplina.setDescricao(request.descricao().trim());
        disciplina.setNome(request.sigla().trim());
        disciplina.setCargaHoraria(request.cargaHoraria());
        disciplina.setEscolaId(request.escolaId());
        return toResponse(disciplinaRepository.save(disciplina));
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

    private DisciplinaResponse toResponse(Disciplina disciplina) {
        String escolaNome = null;
        if (disciplina.getEscolaId() != null) {
            escolaNome = escolaRepository.findById(disciplina.getEscolaId())
                    .map(Escola::getNome)
                    .orElse(null);
        }

        List<MatrizResumida> matrizes = matrizDisciplinaRepository
                .findByDisciplinaId(disciplina.getId())
                .stream()
                .map(md -> new MatrizResumida(md.getMatriz().getId(), md.getMatriz().getCodigo()))
                .toList();

        return new DisciplinaResponse(
                disciplina.getId(),
                disciplina.getSigla(),
                disciplina.getDescricao(),
                disciplina.getCargaHoraria(),
                disciplina.getDataCadastro(),
                disciplina.getStatus(),
                disciplina.getEscolaId(),
                escolaNome,
                matrizes);
    }
}
