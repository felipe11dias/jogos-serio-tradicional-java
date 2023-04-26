package br.unifor.enviromentgameserius.tcc.rest.service;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityListResponse;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterRequest;
import br.unifor.enviromentgameserius.tcc.rest.dto.ActivityRegisterResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActivityService {

    Page<ActivityListResponse> list(Pageable pagination);
    ActivityRegisterResponse register(ActivityRegisterRequest request, Discipline discipline, User user);
    ActivityRegisterResponse edit(Activity activity, Discipline discipline, @Valid ActivityRegisterRequest request);
    Optional<Discipline> getDiscipline(Long id);
    Optional<User> getUser(Long id);
    Optional<Activity> getActivity(Long id);
    void delete(Long id);
}
