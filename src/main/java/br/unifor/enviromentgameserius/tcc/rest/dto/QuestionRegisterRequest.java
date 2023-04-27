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
public class QuestionRegisterRequest {

    @NotEmpty
    private String description;

    @NotEmpty
    private List<AnswerRegisterRequest> answers;

    @NotEmpty
    private String idAnswerCorrect;
}
