package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEditRequest {

    private Long id;

    @NotNull(message = "A descrição da resposta não pode ser vazia.")
    @Length(min = 1, message = "A descrição da resposta deve ter no minimo um caracter.")
    private String description;
}
