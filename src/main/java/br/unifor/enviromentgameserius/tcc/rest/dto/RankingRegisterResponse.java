package br.unifor.enviromentgameserius.tcc.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingRegisterResponse {

    private Long id;
    private String game;
    private String time;
    private String fullTime;
    private Integer questionsHit;
    private String activity;
    private String user;
}
