package br.com.msacademico.service;

import br.com.msacademico.dto.CursoResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.dto.MatrizRequest;
import br.com.msacademico.dto.MatrizResponse;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Curso;
import br.com.msacademico.model.Escola;
import br.com.msacademico.model.Matriz;
import br.com.msacademico.repository.CursoRepository;
import br.com.msacademico.repository.MatrizRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatrizService {

    private final MatrizRepository matrizRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public MatrizResponse criar(MatrizRequest request) {
        Curso curso = buscarCursoPorId(request.cursoId());
        Matriz matriz = Matriz.builder()
                .codigo(request.codigo().trim())
                .curso(curso)
                .build();

        return toResponse(matrizRepository.save(matriz));
    }

    @Transactional(readOnly = true)
    public Page<MatrizResponse> listar(Pageable pageable) {
        return matrizRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<MatrizResponse> listarTodas() {
        return matrizRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatrizResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public MatrizResponse atualizar(Long id, MatrizRequest request) {
        Matriz matriz = buscarEntidadePorId(id);
        Curso curso = buscarCursoPorId(request.cursoId());

        matriz.setCodigo(request.codigo().trim());
        matriz.setCurso(curso);

        return toResponse(matrizRepository.save(matriz));
    }

    @Transactional
    public void excluir(Long id) {
        Matriz matriz = buscarEntidadePorId(id);
        matrizRepository.delete(matriz);
    }

    private Matriz buscarEntidadePorId(Long id) {
        return matrizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matriz nao encontrada com id: " + id));
    }

    private Curso buscarCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso nao encontrado com id: " + id));
    }

    private MatrizResponse toResponse(Matriz matriz) {
        Curso curso = matriz.getCurso();
        Escola escola = curso.getEscola();
        EscolaResponse escolaResponse = new EscolaResponse(escola.getId(), escola.getNome());
        CursoResponse cursoResponse = new CursoResponse(curso.getId(), curso.getNome(), escolaResponse);
        return new MatrizResponse(matriz.getId(), matriz.getCodigo(), cursoResponse);
    }
}