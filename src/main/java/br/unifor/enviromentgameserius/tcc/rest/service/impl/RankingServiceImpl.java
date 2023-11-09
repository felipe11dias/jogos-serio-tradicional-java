package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.RankingRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.RankingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository repository;

    @Override
    public Page<RankingListResponse> list(Pageable pagination) {
        Page<Ranking> ratings = repository.findAll(pagination);
        return ratings.map(RankingListResponse::new);
    }

    @Override
    @Transactional
    public RankingRegisterResponse register(RankingRegisterRequest request, User user, Activity activity) {
        Ranking ranking = Ranking.builder()
                .game(request.getGame())
                .time(request.getTime())
                .fulltime(request.getFulltime())
                .questionsHit(request.getQuestionsHit())
                .questions(activity.getQuestions().size())
                .discipline(activity.getDiscipline().getName())
                .activity(activity.getName())
                .user(user)
                .build();

        repository.save(ranking);

        return RankingRegisterResponse.builder()
                .game(ranking.getGame())
                .time(ranking.getTime())
                .fulltime(ranking.getFulltime())
                .questionsHit(ranking.getQuestionsHit())
                .questions(ranking.getQuestions())
                .discipline(ranking.getDiscipline())
                .activity(ranking.getActivity())
                .user(ranking.getUser().getName())
                .build();
    }

    @Override
    @Transactional
    public RankingRegisterResponse edit(RankingRegisterRequest request, Ranking ranking, User user) {
        ranking.setGame(request.getGame());
        ranking.setTime(request.getTime());
        ranking.setFulltime(request.getFulltime());
        ranking.setQuestionsHit(request.getQuestionsHit());
        ranking.setUser(user);

        repository.save(ranking);

        return RankingRegisterResponse.builder()
                .game(ranking.getGame())
                .time(ranking.getTime())
                .fulltime(ranking.getFulltime())
                .questionsHit(ranking.getQuestionsHit())
                .user(ranking.getUser().getName())
                .build();
    }

    public void delete(Long id) {
        Optional<Ranking> ranking = repository.findById(id);
        if(ranking.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Optional<Ranking> getRanking(Long id) {
        return Optional.of(repository.findById(id)).get();
    }

}
