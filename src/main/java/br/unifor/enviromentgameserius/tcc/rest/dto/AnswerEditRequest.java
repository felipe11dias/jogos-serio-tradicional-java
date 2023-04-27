package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerEditRequest {

    private Long id;

    @NotEmpty
    private String description;
}
