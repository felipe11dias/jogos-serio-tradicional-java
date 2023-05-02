package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestionEditRequest {

    private Long id;

    @NotNull(message = "A descrição da questão não pode ser vazia.")
    @Length(min = 1, message = "A descrição da questão deve ter no minimo um caracter.")
    private String description;

    @NotEmpty
    private List<AnswerEditRequest> answers;

    @NotEmpty
    private String idAnswerCorrect;
}
