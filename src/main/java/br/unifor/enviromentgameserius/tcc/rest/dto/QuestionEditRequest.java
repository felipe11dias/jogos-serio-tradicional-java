package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEditRequest {

    private Long id;

    @NotEmpty
    private String description;

    @NotEmpty
    private List<AnswerEditRequest> answers;

    @NotEmpty
    private String idAnswerCorrect;
}
