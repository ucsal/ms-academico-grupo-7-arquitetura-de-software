package br.com.msacademico.repository;

import br.com.msacademico.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
