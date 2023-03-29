package br.unifor.enviromentgameserius.tcc.rest.service.impl;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.Questioner;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.domain.repository.ActivityRepository;
import br.unifor.enviromentgameserius.tcc.domain.repository.DisciplineRepository;
import br.unifor.enviromentgameserius.tcc.domain.repository.UserRepository;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterResponse;
import br.unifor.enviromentgameserius.tcc.rest.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository repository;

    private final DisciplineRepository disciplineRepository;

    private final UserRepository userRepository;

    @Override
    public ActivityListResponse listActivities() {
        Optional<List<Activity>> listActivities = Optional.of(repository.findAll());

        if(listActivities.get().isEmpty()) {
            return null;
        }

        return ActivityListResponse.builder()
                .activities(listActivities.get())
                .build();
    }

    @Override
    public ActivityRegisterResponse register(ActivityRegisterRequest request, Discipline discipline, User user) {
        var activities = Activity.builder()
                .name(request.getName())
                .discipline(discipline)
                .user(user)
                .questioner(request.getQuestioner())
                .build();

        repository.save(activities);

        return ActivityRegisterResponse.builder()
                .id(activities.getId())
                .name(activities.getName())
                .discipline(activities.getDiscipline())
                .user(activities.getUser())
                .questioner(activities.getQuestioner())
                .build();
    }

    @Override
    public Optional<Activity> getActivity(Long id) {
        return Optional.of(repository.findById(id).get());
    }

    @Override
    public Optional<Discipline> getDiscipline(Long id) {
        return Optional.of(disciplineRepository.findById(id).get());
    }

    @Override
    public Optional<User> getUser(Long id) {
        return Optional.of(userRepository.findById(id).get());
    }

}
