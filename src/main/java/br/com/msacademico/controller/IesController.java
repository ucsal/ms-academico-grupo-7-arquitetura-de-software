package br.com.msacademico.controller;

import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.IesRequest;
import br.com.msacademico.dto.IesResponse;
import br.com.msacademico.service.IesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/ies")
@RequiredArgsConstructor
@Validated
public class IesController {

    private final IesService iesService;

    @PostMapping
    public ResponseEntity<ApiResponse<IesResponse>> salvar(@Valid @RequestBody IesRequest request) {
        IesResponse response = iesService.salvar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.of("IES criada com sucesso.", response));
    }

    @GetMapping
    public ResponseEntity<List<IesResponse>> listarTodos() {
        return ResponseEntity.ok(iesService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<IesResponse>> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id) {
        return ResponseEntity.ok(ApiResponse.of("IES encontrada com sucesso.", iesService.buscarPorId(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<IesResponse>> atualizar(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id,
            @Valid @RequestBody IesRequest request) {
        return ResponseEntity.ok(ApiResponse.of("IES atualizada com sucesso.", iesService.atualizar(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id) {
        iesService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
