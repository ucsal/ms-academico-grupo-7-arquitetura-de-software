package br.com.msacademico.controller;

import br.com.msacademico.dto.ApiResponse;
import br.com.msacademico.dto.MatrizDisciplinaLoteRequest;
import br.com.msacademico.dto.MatrizDisciplinaRequest;
import br.com.msacademico.dto.MatrizDisciplinaResponse;
import br.com.msacademico.service.MatrizDisciplinaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
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

@RestController
@RequestMapping("/api/matriz-disciplinas")
@RequiredArgsConstructor
@Validated
public class MatrizDisciplinaController {

    private final MatrizDisciplinaService matrizDisciplinaService;

    @PostMapping("/lote")
    public ResponseEntity<ApiResponse<List<MatrizDisciplinaResponse>>> salvarEmLote(
            @Valid @RequestBody MatrizDisciplinaLoteRequest request
    ) {
        return ResponseEntity.status(201).body(ApiResponse.of(
                "Disciplinas vinculadas a matriz com sucesso.",
                matrizDisciplinaService.salvarEmLote(request)
        ));
    }

    @GetMapping("/matriz/{matrizId}")
    public ResponseEntity<ApiResponse<List<MatrizDisciplinaResponse>>> listarPorMatriz(
            @PathVariable @Positive(message = "O matrizId deve ser maior que zero.") Long matrizId
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Disciplinas vinculadas a matriz listadas com sucesso.",
                matrizDisciplinaService.listarPorMatriz(matrizId)
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MatrizDisciplinaResponse>> buscarPorId(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Vinculo entre matriz e disciplina encontrado com sucesso.",
                matrizDisciplinaService.buscarPorId(id)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MatrizDisciplinaResponse>> atualizar(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id,
            @Valid @RequestBody MatrizDisciplinaRequest request
    ) {
        return ResponseEntity.ok(ApiResponse.of(
                "Vinculo entre matriz e disciplina atualizado com sucesso.",
                matrizDisciplinaService.atualizar(id, request)
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable @Positive(message = "O id deve ser maior que zero.") Long id
    ) {
        matrizDisciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
