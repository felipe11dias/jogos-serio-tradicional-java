package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Discipline;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplineListResponse {

    private Long id;
    private String name;
    private String theme;
    private String user;
    private Long idUser;

    public DisciplineListResponse(Discipline discipline) {
        this.id = discipline.getId();
        this.name = discipline.getName();
        this.theme = discipline.getTheme();
        this.user = discipline.getUser().getName();
        this.idUser = discipline.getUser().getId();
    }
}
