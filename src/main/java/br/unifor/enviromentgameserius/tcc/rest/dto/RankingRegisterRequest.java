package br.unifor.enviromentgameserius.tcc.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingRegisterRequest {

    @NotNull(message = "Nome do jogo é obrigatório")
    private String game;

    @NotNull(message = "Tempo de finalização de jogo da atividade é obrigatório")
    private String time;

    @NotNull(message = "Tempo final de finalização do jogo da atividade é obrigatório")
    private String fullTime;

    @NotNull(message = "Quantidade de questões corretas é obrigatório")
    private Integer questionsHit;

    @NotNull(message = "Atividade é obrigatório")
    private Long idActivity;

    @NotNull(message = "Usuário é obrigatório")
    private Long idUser;
}
