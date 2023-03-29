package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Activity;
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
public class DisciplineRegisterRequest {

    @NotEmpty
    private String name;

    @NotEmpty
    private String theme;

    @NotNull
    private Long idUser;
}
