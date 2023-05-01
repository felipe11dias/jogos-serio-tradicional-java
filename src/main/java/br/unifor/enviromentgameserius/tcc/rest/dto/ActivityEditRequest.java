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
public class ActivityEditRequest {

    @NotNull(message = "O nome não pode ser vazio.")
    @Length(min = 3, message = "O nome da atividade deve ter no minimo três caracters.")
    private String name;

    @NotNull
    private String idDiscipline;

    @NotNull
    private Long idUser;

    @NotEmpty
    private List<QuestionEditRequest> questions;

}
