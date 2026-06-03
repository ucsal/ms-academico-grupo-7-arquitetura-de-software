package br.com.msacademico.client;

import br.com.msacademico.dto.AlunoResponse;
import br.com.msacademico.dto.ProfessorResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pessoas", url = "http://localhost:8083")
public interface PessoasClient {

    @GetMapping("/alunos/{id}")
    AlunoResponse buscarAlunoPorId(@PathVariable Long id);

    @GetMapping("/professores/{id}")
    ProfessorResponse buscarProfessorPorId(@PathVariable Long id);
}