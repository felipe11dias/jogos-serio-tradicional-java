package br.unifor.enviromentgameserius.tcc.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "QUESTIONER")
public class Questioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questioner_id")
    private Long id;

    private List<Question> questions;
}
