package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.Question;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityListResponse {

    private Long id;
    private String name;
    private List<QuestionListResponse> questions;
    private Long discipline;
    private Long user;

    public ActivityListResponse(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.user = activity.getUser().getId();
        this.discipline = activity.getDiscipline().getId();
        this.questions = activity.getQuestions().stream().map(QuestionListResponse::new).toList();
    }
}
