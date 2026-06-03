package br.com.msacademico.controller;

import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.DisciplinaRequest;
import br.com.msacademico.dto.DisciplinaResponse;
import br.com.msacademico.service.DisciplinaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/disciplinas")
@RequiredArgsConstructor
@Validated
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    @PostMapping
    public ResponseEntity<ApiResponse<DisciplinaResponse>> criar(@Valid @RequestBody DisciplinaRequest request) {
        DisciplinaResponse response = disciplinaService.criar(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location)
                .body(ApiResponse.of("Disciplina criada com sucesso.", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<DisciplinaResponse>>> listar(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Disciplinas listadas com sucesso.",
                disciplinaService.listar(pageable)
        ));
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<ApiResponse<List<DisciplinaResponse>>> listarDisponiveis(
            @RequestParam @Positive(message = "O alunoId deve ser maior que zero.") Long alunoId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Disciplinas disponiveis listadas com sucesso.",
                disciplinaService.listarDisponiveis(alunoId)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DisciplinaResponse>> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Disciplina encontrada com sucesso.",
                disciplinaService.buscarPorId(id)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        disciplinaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
