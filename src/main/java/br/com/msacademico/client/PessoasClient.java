package br.com.msacademico.client;

import br.com.msacademico.dto.AlunoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-pessoas")
public interface PessoasClient {

    @GetMapping("/alunos/{id}")
    AlunoResponse buscarAlunoPorId(@PathVariable Long id);
}
