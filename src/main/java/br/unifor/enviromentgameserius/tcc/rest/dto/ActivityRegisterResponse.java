package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import br.unifor.enviromentgameserius.tcc.domain.model.Questioner;
import br.unifor.enviromentgameserius.tcc.domain.model.User;
import jakarta.persistence.Column;
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
public class ActivityRegisterResponse {

    @NotNull
    private Long id;

    @NotEmpty
    @Column(name = "name", unique = true)
    private String name;

    @NotEmpty
    private Discipline discipline;

    private Questioner questioner;

    private User user;

}
