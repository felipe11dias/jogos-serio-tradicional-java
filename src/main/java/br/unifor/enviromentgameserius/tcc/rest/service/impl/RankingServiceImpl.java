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
                .fullTime(request.getFullTime())
                .questionsHit(request.getQuestionsHit())
                .activity(activity)
                .user(user)
                .build();

        repository.save(ranking);

        return RankingRegisterResponse.builder()
                .game(ranking.getGame())
                .time(ranking.getTime())
                .fullTime(ranking.getFullTime())
                .questionsHit(ranking.getQuestionsHit())
                .activity(ranking.getActivity().getName())
                .user(ranking.getUser().getName())
                .build();
    }

    @Override
    @Transactional
    public RankingRegisterResponse edit(RankingRegisterRequest request, Ranking ranking, User user, Activity activity) {
        ranking.setGame(request.getGame());
        ranking.setTime(request.getTime());
        ranking.setFullTime(request.getFullTime());
        ranking.setQuestionsHit(request.getQuestionsHit());
        ranking.setUser(user);
        ranking.setActivity(activity);

        repository.save(ranking);

        return RankingRegisterResponse.builder()
                .game(ranking.getGame())
                .time(ranking.getTime())
                .fullTime(ranking.getFullTime())
                .questionsHit(ranking.getQuestionsHit())
                .activity(ranking.getActivity().getName())
                .user(ranking.getUser().getName())
                .build();
    }

    @Transactional
    public void delete(Long id) {
        Optional<Ranking> ranking = repository.findById(id);
        if(ranking.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Optional<Ranking> getRankinByUserAndActivity(User user, Activity activity) {
        return repository.findByUserAndActivity(user, activity);
    }

}
