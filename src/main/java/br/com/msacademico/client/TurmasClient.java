package br.com.msacademico.client;

import br.com.msacademico.dto.EspacoFisicoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "ms-turmas")
public interface TurmasClient {

    @GetMapping("/api/espacos-fisicos/disponiveis")
    List<EspacoFisicoResponse> listarEspacos();
}