package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Endereco;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;

import java.util.List;

public record PaquimetroDTO (
        String id,
        Endereco endereco,
        Float valor,
        List<Vaga>vagas,
        Long version
){
}
