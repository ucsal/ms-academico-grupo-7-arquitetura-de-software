package br.com.msacademico.repository;

import br.com.msacademico.model.DisciplinaProfessor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplinaProfessorRepository extends JpaRepository<DisciplinaProfessor, Long> {

    boolean existsByDisciplinaIdAndProfessorId(Long disciplinaId, Long professorId);

    List<DisciplinaProfessor> findByDisciplinaId(Long disciplinaId);

    Optional<DisciplinaProfessor> findByDisciplinaIdAndProfessorId(Long disciplinaId, Long professorId);
}
