package br.unifor.enviromentgameserius.tcc.domain.repository;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Optional<Ranking> findByUserAndActivityAndGame(User user, Activity activity, String game);
}
