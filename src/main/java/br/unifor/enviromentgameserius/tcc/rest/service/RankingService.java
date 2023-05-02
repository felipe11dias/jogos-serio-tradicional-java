package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Question;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;

import java.util.List;

public interface RankingService {

    RankingRegisterResponse register(RankingRegisterRequest request, List<Question> questions, User user, Activity activity);
    List<Question> getQuestions(List<Long> idQuestions);
}
