package br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MotoristaRepository extends MongoRepository<Motorista, String> {
}
