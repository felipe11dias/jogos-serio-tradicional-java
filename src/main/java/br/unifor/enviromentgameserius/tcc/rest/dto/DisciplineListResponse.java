package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineListResponse {

    private List<Discipline> disciplines;
}
