package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Question;
import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.QuestionRepository;
import br.unifor.enviromentgameserius.tcc.domain.repository.RankingRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.QuestionListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.RankingRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.RankingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository repository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public RankingRegisterResponse register(RankingRegisterRequest request, List<Question> questions, User user, Activity activity) {
        Ranking ranking = Ranking.builder()
                .time(request.getTime())
                .questionsHit(questions)
                .activity(activity)
                .user(user)
                .build();

        repository.save(ranking);

        return RankingRegisterResponse.builder()
                .time(ranking.getTime())
                .activity(ranking.getActivity().getName())
                .user(ranking.getUser().getName())
                .questionsHit(ranking.getQuestionsHit().stream().map(QuestionListResponse::new).toList())
                .build();
    }

    @Override
    public List<Question> getQuestions(List<Long> idQuestions) {
        return idQuestions.stream().map(id -> {
            Optional<Question> questionOptional = questionRepository.findById(id);
            if(questionOptional.isPresent()) {
                return questionOptional.get();
            }

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ranking inválido. Questão não encontrada.");
        }).toList();
    }

}
