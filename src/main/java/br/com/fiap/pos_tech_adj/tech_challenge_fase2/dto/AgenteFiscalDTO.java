package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;

public record AgenteFiscalDTO (
        String id,

        Pessoa pessoa,

        Long version
){
}
