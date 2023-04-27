package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Question;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionListResponse {

    private Long id;
    private String description;
    private List<AnswerListResponse> answers;
    private String idAnswerCorrect;

    public QuestionListResponse(Question question) {
        this.id = question.getId();
        this.description = question.getDescription();
        this.answers = question.getAnswers().stream().map(AnswerListResponse::new).toList();
        this.idAnswerCorrect = question.getIdAnswerCorrect().toString();
    }
}
