package br.com.msacademico.service;

import br.com.msacademico.dto.MatrizDisciplinaLoteRequest;
import br.com.msacademico.dto.MatrizDisciplinaRequest;
import br.com.msacademico.dto.MatrizDisciplinaResponse;
import br.com.msacademico.exception.BusinessException;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Disciplina;
import br.com.msacademico.model.Matriz;
import br.com.msacademico.model.MatrizDisciplina;
import br.com.msacademico.repository.DisciplinaRepository;
import br.com.msacademico.repository.MatrizDisciplinaRepository;
import br.com.msacademico.repository.MatrizRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MatrizDisciplinaService {

    private final MatrizDisciplinaRepository matrizDisciplinaRepository;
    private final MatrizRepository matrizRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Transactional
    public List<MatrizDisciplinaResponse> salvarEmLote(MatrizDisciplinaLoteRequest request) {
        Matriz matriz = buscarMatrizPorId(request.matrizId());

        return request.disciplinaIds().stream()
                .map(disciplinaId -> salvarVinculo(matriz, disciplinaId))
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatrizDisciplinaResponse> listarPorMatriz(Long matrizId) {
        buscarMatrizPorId(matrizId);
        return matrizDisciplinaRepository.findByMatrizId(matrizId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatrizDisciplinaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public MatrizDisciplinaResponse atualizar(Long id, MatrizDisciplinaRequest request) {
        MatrizDisciplina matrizDisciplina = buscarEntidadePorId(id);
        Matriz matriz = buscarMatrizPorId(request.matrizId());
        Disciplina disciplina = buscarDisciplinaPorId(request.disciplinaId());

        if (!matrizDisciplina.getMatriz().getId().equals(request.matrizId())
                || !matrizDisciplina.getDisciplina().getId().equals(request.disciplinaId())) {
            validarVinculoInexistente(request.matrizId(), request.disciplinaId());
        }

        matrizDisciplina.setMatriz(matriz);
        matrizDisciplina.setDisciplina(disciplina);

        return toResponse(matrizDisciplinaRepository.save(matrizDisciplina));
    }

    @Transactional
    public void deletar(Long id) {
        MatrizDisciplina matrizDisciplina = buscarEntidadePorId(id);
        matrizDisciplinaRepository.delete(matrizDisciplina);
    }

    private MatrizDisciplina salvarVinculo(Matriz matriz, Long disciplinaId) {
        Disciplina disciplina = buscarDisciplinaPorId(disciplinaId);
        validarVinculoInexistente(matriz.getId(), disciplinaId);

        MatrizDisciplina matrizDisciplina = MatrizDisciplina.builder()
                .matriz(matriz)
                .disciplina(disciplina)
                .build();

        return matrizDisciplinaRepository.save(matrizDisciplina);
    }

    private void validarVinculoInexistente(Long matrizId, Long disciplinaId) {
        if (matrizDisciplinaRepository.existsByMatrizIdAndDisciplinaId(matrizId, disciplinaId)) {
            throw new BusinessException("Disciplina ja vinculada a matriz informada.");
        }
    }

    private MatrizDisciplina buscarEntidadePorId(Long id) {
        return matrizDisciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Vinculo entre matriz e disciplina nao encontrado com id: " + id
                ));
    }

    private Matriz buscarMatrizPorId(Long id) {
        return matrizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matriz nao encontrada com id: " + id));
    }

    private Disciplina buscarDisciplinaPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina nao encontrada com id: " + id));
    }

    private MatrizDisciplinaResponse toResponse(MatrizDisciplina matrizDisciplina) {
        Matriz matriz = matrizDisciplina.getMatriz();
        Disciplina disciplina = matrizDisciplina.getDisciplina();

        return new MatrizDisciplinaResponse(
                matrizDisciplina.getId(),
                matriz.getId(),
                matriz.getCodigo(),
                disciplina.getId(),
                disciplina.getNome()
        );
    }
}
