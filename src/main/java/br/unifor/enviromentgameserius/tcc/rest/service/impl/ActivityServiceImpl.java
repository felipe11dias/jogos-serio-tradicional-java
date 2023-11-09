package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.*;
import br.unifor.enviromentgameserius.tcc.domain.repository.*;
import br.unifor.enviromentgameserius.tcc.rest.dto.*;
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
    public Page<ActivityListResponse> listByDiscipline(Discipline discipline, Pageable pagination) {
        Page<Activity> activities = repository.findByDiscipline(discipline, pagination);
        return activities.map(ActivityListResponse::new);
    }

    @Override
    public Page<ActivityListResponse> listByDisciplineAndName(Discipline discipline, String activity, Pageable pagination) {
        Page<Activity> activities = repository.findByDisciplineAndName(discipline, activity, pagination);
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
                    .description(question.getDescription() + " ?")
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
            registerQuestion.setIdAnswerCorrect(answers.get(Long.valueOf(question.getIdAnswerCorrect()).intValue()).getId());

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
    public ActivityRegisterResponse edit(Activity activity, Discipline discipline, ActivityEditRequest request) {
        activity.setName(request.getName());
        activity.setDiscipline(discipline);

        repository.save(activity);

        var questions = request.getQuestions().stream().map( question -> {
            Optional<Question> editQuestion = questionRepository.findById(question.getId());
            if(editQuestion.isPresent()) {
                editQuestion.get().setDescription(question.getDescription());
                var answers = question.getAnswers().stream().map(answer -> {
                    Optional<Answer> editAnswer = answerRepository.findById(answer.getId());
                    if(editAnswer.isPresent()) {
                        editAnswer.get().setDescription(answer.getDescription());
                        return editAnswer.get();
                    }
                    return null;
                }).toList();

                questionRepository.save(editQuestion.get());
                answerRepository.saveAll(answers);
                editQuestion.get().setAnswers(answers);
                editQuestion.get().setIdAnswerCorrect(Long.valueOf(question.getIdAnswerCorrect()));

                return editQuestion.get();
            }
            return null;
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
    public void delete(Long id) {
        Optional<Activity> activity = repository.findById(id);
        if(activity.isPresent()) {
            repository.deleteById(id);
        }
    }

    @Override
    public Page<ActivityListResponse> listByName(String activity, Pageable pagination) {
        Page<Activity> activities = repository.findByNameContaining(activity, pagination);
        return activities.map(ActivityListResponse::new);
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
