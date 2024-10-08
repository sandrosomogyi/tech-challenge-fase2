package br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.AgenteFiscal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AgenteFiscalRepository extends MongoRepository<AgenteFiscal, String> {

    // Método para buscar AgentesFiscais pelo ID de Pessoa
    Optional<AgenteFiscal> findByPessoa_Id(String pessoaId);
}
