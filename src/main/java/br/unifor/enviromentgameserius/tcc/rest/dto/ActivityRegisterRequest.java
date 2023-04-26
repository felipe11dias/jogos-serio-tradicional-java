package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRegisterRequest {

    @NotEmpty
    private String name;

    @NotNull
    private Long idDiscipline;

    @NotNull
    private Long idUser;

    @NotEmpty
    private List<RegisterQuestionRequest> questions;

}
