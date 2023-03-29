package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.Questioner;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRegisterRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private Long idDiscipline;

    @NotEmpty
    private Long idUser;

    @NotEmpty
    private Questioner questioner;

}
