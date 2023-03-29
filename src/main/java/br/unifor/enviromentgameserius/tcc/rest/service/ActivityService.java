package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterResponse;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    Optional<List<Activity>> list();
    ActivityRegisterResponse register(ActivityRegisterRequest request, Discipline discipline, User user);
    Optional<Discipline> getDiscipline(Long id);
    Optional<User> getUser(Long id);
    Optional<Activity> getActivity(Long id);
}
