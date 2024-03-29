package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
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
    private String discipline;
    private Long idDiscipline;
    private String user;
    private Long idUser;

    public ActivityListResponse(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.user = activity.getUser().getName();
        this.idUser = activity.getUser().getId();
        this.discipline = activity.getDiscipline().getName();
        this.idDiscipline = activity.getDiscipline().getId();
        this.questions = activity.getQuestions().stream().map(QuestionListResponse::new).toList();
    }
}
