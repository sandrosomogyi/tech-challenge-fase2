package br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PessoaRepository extends MongoRepository<Pessoa, String> {

    // Definição do método de busca por email e senha
    Optional<Pessoa> findByEmailAndSenha(String email, String senha);
}
