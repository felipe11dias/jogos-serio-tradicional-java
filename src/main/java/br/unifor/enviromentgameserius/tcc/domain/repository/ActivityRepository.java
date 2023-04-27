package br.unifor.enviromentgameserius.tcc.domain.repository;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Page<Activity> findByDiscipline(Discipline discipline, Pageable paginacao);

}
