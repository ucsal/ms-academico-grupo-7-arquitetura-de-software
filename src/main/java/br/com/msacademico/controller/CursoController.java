package br.com.msacademico.controller;

import br.com.msacademico.dto.CursoRequest;
import br.com.msacademico.dto.CursoResponse;
import br.com.msacademico.service.CursoService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@Validated
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoResponse> criar(@Valid @RequestBody CursoRequest request) {
        CursoResponse response = cursoService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoResponse> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        cursoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
