package br.unifor.enviromentgameserius.tcc.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "answer")
    @OneToMany(mappedBy = "question")
    private List<Answer> answer;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Questioner questioner;

    @NotNull
    @Column(name = "answer_correct_id")
    private Long idAnswerCorrect;

}
