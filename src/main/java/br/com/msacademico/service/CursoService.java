package br.com.msacademico.service;

import br.com.msacademico.dto.CursoRequest;
import br.com.msacademico.dto.CursoResponse;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Curso;
import br.com.msacademico.model.Escola;
import br.com.msacademico.repository.CursoRepository;
import br.com.msacademico.repository.EscolaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final EscolaRepository escolaRepository;

    @Transactional
    public CursoResponse criar(CursoRequest request) {
        Escola escola = buscarEscolaPorId(request.escolaId());
        Curso curso = Curso.builder()
                .nome(request.nome().trim())
                .escola(escola)
                .build();

        return toResponse(cursoRepository.save(curso));
    }

    @Transactional(readOnly = true)
    public Page<CursoResponse> listar(Pageable pageable) {
        return cursoRepository.findAll(pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public CursoResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public CursoResponse atualizar(Long id, CursoRequest request) {
        Curso curso = buscarEntidadePorId(id);
        Escola escola = buscarEscolaPorId(request.escolaId());

        curso.setNome(request.nome().trim());
        curso.setEscola(escola);

        return toResponse(cursoRepository.save(curso));
    }

    @Transactional
    public void excluir(Long id) {
        Curso curso = buscarEntidadePorId(id);
        cursoRepository.delete(curso);
    }

    private Curso buscarEntidadePorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso nao encontrado com id: " + id));
    }

    private Escola buscarEscolaPorId(Long id) {
        return escolaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola nao encontrada com id: " + id));
    }

    private CursoResponse toResponse(Curso curso) {
        Escola escola = curso.getEscola();
        EscolaResponse escolaResponse = new EscolaResponse(escola.getId(), escola.getNome());
        return new CursoResponse(curso.getId(), curso.getNome(), escolaResponse);
    }
}
