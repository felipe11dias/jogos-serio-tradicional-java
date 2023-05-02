package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotEmpty;
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
public class DisciplineRegisterRequest {

    @NotNull(message = "O nome não pode ser vazio.")
    @Length(min = 3, message = "O nome da disciplina deve ter no minimo três caracters.")
    private String name;

    @NotEmpty
    private String theme;

    private Long idUser;
}
