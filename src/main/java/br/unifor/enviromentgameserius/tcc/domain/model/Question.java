package br.unifor.enviromentgameserius.tcc.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"QUESTION\"")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @NotEmpty
    private String description;


    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers;

//    @ManyToOne
//    @JoinColumn(name = "ranking_id")
//    private Ranking ranking;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @NotNull
    @Column(name = "answer_correct_id")
    private Long idAnswerCorrect;

}
