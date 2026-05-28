package br.com.msacademico.controller;

import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.EscolaRequest;
import br.com.msacademico.dto.EscolaResponse;
import br.com.msacademico.service.EscolaService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/escolas")
@RequiredArgsConstructor
@Validated
public class EscolaController {

    private final EscolaService escolaService;

    @PostMapping
    public ResponseEntity<ApiResponse<EscolaResponse>> criar(@Valid @RequestBody EscolaRequest request) {
        EscolaResponse response = escolaService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.of("Escola criada com sucesso.", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EscolaResponse>>> listar(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.of("Escolas listadas com sucesso.", escolaService.listar(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EscolaResponse>> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        return ResponseEntity.ok(ApiResponse.of("Escola encontrada com sucesso.", escolaService.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        escolaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
