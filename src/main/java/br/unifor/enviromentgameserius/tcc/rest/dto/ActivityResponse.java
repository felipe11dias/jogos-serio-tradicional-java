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
public class ActivityResponse {
    private Long id;
    private String name;
    private List<QuestionListResponse> questions;
    private String idDiscipline;
    private Long idUser;

    public ActivityResponse(Activity activity) {
        this.id = activity.getId();
        this.name = activity.getName();
        this.idUser = activity.getUser().getId();
        this.idDiscipline = activity.getDiscipline().getId().toString();
        this.questions = activity.getQuestions().stream().map(QuestionListResponse::new).toList();
    }

}
