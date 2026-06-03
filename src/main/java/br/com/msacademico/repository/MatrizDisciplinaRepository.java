package br.com.msacademico.repository;

import br.com.msacademico.model.MatrizDisciplina;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatrizDisciplinaRepository extends JpaRepository<MatrizDisciplina, Long> {

    List<MatrizDisciplina> findByMatrizId(Long matrizId);

    boolean existsByMatrizIdAndDisciplinaId(Long matrizId, Long disciplinaId);
}
