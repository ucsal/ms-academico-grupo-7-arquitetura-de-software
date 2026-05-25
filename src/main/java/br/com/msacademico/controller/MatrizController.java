package br.com.msacademico.controller;

import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.MatrizRequest;
import br.com.msacademico.dto.MatrizResponse;
import br.com.msacademico.service.MatrizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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
@RequestMapping("/matrizes")
@RequiredArgsConstructor
@Validated
public class MatrizController {

    private final MatrizService matrizService;

    @PostMapping
    public ResponseEntity<ApiResponse<MatrizResponse>> criar(@Valid @RequestBody MatrizRequest request) {
        MatrizResponse response = matrizService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.of("Matriz criada com sucesso.", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<MatrizResponse>>> listar(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.of("Matrizes listadas com sucesso.", matrizService.listar(pageable)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MatrizResponse>> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        return ResponseEntity.ok(ApiResponse.of("Matriz encontrada com sucesso.", matrizService.buscarPorId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        matrizService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
