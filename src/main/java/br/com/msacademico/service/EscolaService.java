package br.com.msacademico.service;

import br.com.msacademico.dto.EscolaRequest;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Escola;
import br.com.msacademico.repository.EscolaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EscolaService {

    private final EscolaRepository escolaRepository;

    @Transactional
    public EscolaResponse criar(EscolaRequest request) {
        Escola escola = Escola.builder()
                .nome(request.nome().trim())
                .build();

        return toResponse(escolaRepository.save(escola));
    }

    @Transactional(readOnly = true)
    public List<EscolaResponse> listar() {
        return escolaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EscolaResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public void excluir(Long id) {
        Escola escola = buscarEntidadePorId(id);
        escolaRepository.delete(escola);
    }

    private Escola buscarEntidadePorId(Long id) {
        return escolaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Escola nao encontrada com id: " + id));
    }

    private EscolaResponse toResponse(Escola escola) {
        return new EscolaResponse(escola.getId(), escola.getNome());
    }
}
