package br.unifor.enviromentgameserius.tcc.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"RANKING\"")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ranking_id")
    private Long id;

    @NotNull(message = "Nome do jogo é obrigatório")
    private String game;

    @NotNull(message = "Tempo de finalização de jogo da atividade é obrigatório")
    private String time;

    @NotNull(message = "Tempo final de finalização do jogo da atividade é obrigatório")
    private String fullTime;

    @NotNull(message = "Quantidade de questões corretas é obrigatório")
    private Integer questionsHit;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id")
    private User user;

}
