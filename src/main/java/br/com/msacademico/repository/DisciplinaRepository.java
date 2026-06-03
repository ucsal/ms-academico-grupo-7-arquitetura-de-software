package br.com.msacademico.repository;

import br.com.msacademico.model.Disciplina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {

    List<Disciplina> findByMatrizId(Long matrizId);
}
