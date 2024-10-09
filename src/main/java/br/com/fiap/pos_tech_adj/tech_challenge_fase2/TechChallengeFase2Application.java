package br.com.fiap.pos_tech_adj.tech_challenge_fase2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TechChallengeFase2Application {

	public static void main(String[] args) {
		SpringApplication.run(TechChallengeFase2Application.class, args);
	}

}
