package br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Transacao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransacaoRepository extends MongoRepository<Transacao, String> {

    Transacao findByVaga(String id);

    Optional<Transacao> findFirstByVagaOrderByDataHoraDesc(String id);
}
