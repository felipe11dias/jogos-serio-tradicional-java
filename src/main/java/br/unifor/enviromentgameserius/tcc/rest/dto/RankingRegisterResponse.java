package br.unifor.enviromentgameserius.tcc.rest.dto;

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
public class RankingRegisterResponse {

    private Long id;
    private Timestamp time;
    private List<QuestionListResponse> questionsHit;
    private String activity;
    private String user;
}
