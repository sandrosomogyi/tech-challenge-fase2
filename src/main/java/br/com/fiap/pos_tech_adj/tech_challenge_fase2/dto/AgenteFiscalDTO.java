package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import jakarta.validation.constraints.NotBlank;

public record AgenteFiscalDTO (
        String id,

        Pessoa pessoa,

        Long version
){
}
