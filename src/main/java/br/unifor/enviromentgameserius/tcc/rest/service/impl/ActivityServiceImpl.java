package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.*;
import br.unifor.enviromentgameserius.tcc.domain.repository.*;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.QuestionListResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final DisciplineRepository disciplineRepository;
    private final UserRepository userRepository;

    @Override
    public Page<ActivityListResponse> list(Pageable pagination) {
        Page<Activity> activities = repository.findAll(pagination);
        return activities.map(ActivityListResponse::new);
    }

    @Override
    @Transactional
    public ActivityRegisterResponse register(
            ActivityRegisterRequest request,
            Discipline discipline,
            User user
    ) {
        var activity = Activity.builder()
                .name(request.getName())
                .discipline(discipline)
                .user(user)
                .questions(Collections.emptyList())
                .build();

        repository.save(activity);

        var questions = request.getQuestions().stream().map(question -> {
            Question registerQuestion = Question.builder()
                    .description(question.getDescription())
                    .answers(Collections.emptyList())
                    .idAnswerCorrect(-1L)
                    .activity(activity)
                    .build();

            var answers = question.getAnswers().stream().map(answer -> Answer.builder()
                    .description(answer.getDescription())
                    .question(registerQuestion)
                    .build()).toList();

            questionRepository.save(registerQuestion);
            answerRepository.saveAll(answers);
            registerQuestion.setAnswers(answers);
            registerQuestion.setIdAnswerCorrect(answers.get(question.getIdAnswerCorrect().intValue()).getId());

            return registerQuestion;
        }).toList();

        activity.setQuestions(questions);

        return ActivityRegisterResponse.builder()
                .id(activity.getId())
                .name(activity.getName())
                .discipline(activity.getDiscipline().getId())
                .user(activity.getUser().getId())
                .questions(questions.stream().map(QuestionListResponse::new).toList())
                .build();
    }

    @Override
    public ActivityRegisterResponse edit(Activity activity, Discipline discipline, ActivityRegisterRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Activity> activity = repository.findById(id);
        if(activity.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Optional<Activity> getActivity(Long id) {
        return Optional.of(repository.findById(id)).get();
    }

    @Override
    public Optional<Discipline> getDiscipline(Long id) {
        return Optional.of(disciplineRepository.findById(id)).get();
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.of(userRepository.findById(id)).get();
    }

}
