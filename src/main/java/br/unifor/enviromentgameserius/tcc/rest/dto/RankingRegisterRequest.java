package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingRegisterRequest {

    @NotNull(message = "Tempo é obrigatório")
    private Timestamp time;

    private List<Long> questionsHit;

    @NotNull(message = "Atividade é obrigatório")
    private Long idActivity;

    @NotNull(message = "Usuário é obrigatório")
    private Long idUser;
}
