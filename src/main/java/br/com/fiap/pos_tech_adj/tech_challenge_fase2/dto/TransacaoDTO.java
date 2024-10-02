package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;

import java.time.LocalDate;

public record TransacaoDTO (
        String id,
        Motorista motorista,
        Carro carro,
        Vaga vaga,
        LocalDate data,
        Integer horas,
        Long version
){
}
