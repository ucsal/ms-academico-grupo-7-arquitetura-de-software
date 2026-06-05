package br.com.msacademico.client;

import br.com.msacademico.dto.AlunoResponse;
import br.com.msacademico.dto.ProfessorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-pessoas")
public interface PessoasClient {

    @GetMapping("/api/alunos/{id}")
    AlunoResponse buscarAlunoPorId(@PathVariable Long id);

    @GetMapping("/api/professores/{id}")
    ProfessorResponse buscarProfessorPorId(@PathVariable Long id);

    @GetMapping("/api/alunos")
    List<AlunoResponse> listarAlunos();

    @GetMapping("/api/professores")
    List<ProfessorResponse> listarProfessores();
}