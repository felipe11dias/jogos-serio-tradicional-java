package br.unifor.enviromentgameserius.tcc.domain.repository;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
}
