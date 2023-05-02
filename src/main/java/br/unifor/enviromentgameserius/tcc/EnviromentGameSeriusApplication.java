package br.unifor.enviromentgameserius.tcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class EnviromentGameSeriusApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnviromentGameSeriusApplication.class, args);
	}

}
