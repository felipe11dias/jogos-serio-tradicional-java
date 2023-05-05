package br.unifor.enviromentgameserius.tcc.rest.dto;

import br.unifor.enviromentgameserius.tcc.domain.model.Ranking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RankingListResponse {

    private Long id;
    private String game;
    private String time;
    private String fulltime;
    private Integer questionsHit;
    private String activity;
    private String user;

    public RankingListResponse(Ranking ranking) {
        this.game = ranking.getGame();
        this.time = ranking.getTime();
        this.fulltime = ranking.getFullTime();
        this.questionsHit = ranking.getQuestionsHit();
        this.activity = ranking.getActivity().getName();
        this.user = ranking.getUser().getName();
    }
}
