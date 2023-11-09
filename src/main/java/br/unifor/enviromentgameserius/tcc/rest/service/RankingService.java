package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RankingService {

    Page<RankingListResponse> list(Pageable pagination);
    RankingRegisterResponse register(RankingRegisterRequest request, User user, Activity activity);
    RankingRegisterResponse edit(RankingRegisterRequest request, Ranking ranking, User user);
    void delete(Long id);
    Optional<Ranking> getRanking(Long id);
}
