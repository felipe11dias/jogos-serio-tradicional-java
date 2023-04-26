package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Answer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerListResponse {

    private Long id;
    private String description;

    public AnswerListResponse(Answer answer) {
        this.id = answer.getId();
        this.description = answer.getDescription();
    }
}
