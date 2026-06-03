package br.com.msacademico.service;

import br.com.msacademico.dto.IesRequest;
import br.com.msacademico.dto.IesResponse;
import br.com.msacademico.exception.BusinessException;
import br.com.msacademico.exception.ResourceNotFoundException;
import br.com.msacademico.model.Ies;
import br.com.msacademico.repository.IesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IesService {

    private final IesRepository iesRepository;

    @Transactional
    public IesResponse salvar(IesRequest request) {
        String codigo = request.codigo().trim();
        validarCodigoInexistente(codigo);

        Ies ies = Ies.builder()
                .nome(request.nome().trim())
                .codigo(codigo)
                .build();

        return toResponse(iesRepository.save(ies));
    }

    @Transactional(readOnly = true)
    public List<IesResponse> listarTodos() {
        return iesRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public IesResponse buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public IesResponse atualizar(Long id, IesRequest request) {
        Ies ies = buscarEntidadePorId(id);
        String codigo = request.codigo().trim();

        if (!ies.getCodigo().equals(codigo)) {
            validarCodigoInexistente(codigo);
        }

        ies.setNome(request.nome().trim());
        ies.setCodigo(codigo);

        return toResponse(iesRepository.save(ies));
    }

    @Transactional
    public void deletar(Long id) {
        Ies ies = buscarEntidadePorId(id);
        iesRepository.delete(ies);
    }

    private void validarCodigoInexistente(String codigo) {
        if (iesRepository.existsByCodigo(codigo)) {
            throw new BusinessException("Ja existe uma IES cadastrada com o codigo informado.");
        }
    }

    private Ies buscarEntidadePorId(Long id) {
        return iesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("IES nao encontrada com id: " + id));
    }

    private IesResponse toResponse(Ies ies) {
        return IesResponse.builder()
                .id(ies.getId())
                .nome(ies.getNome())
                .codigo(ies.getCodigo())
                .build();
    }
}
