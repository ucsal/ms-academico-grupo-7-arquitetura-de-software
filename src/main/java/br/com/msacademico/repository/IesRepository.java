package br.com.msacademico.repository;

import br.com.msacademico.model.Ies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IesRepository extends JpaRepository<Ies, Long> {

    boolean existsByCodigo(String codigo);
}
