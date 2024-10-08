package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Transacao;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record MotoristaDTO (
        String id,
        Float saldo,
        List<Carro> carros,
        List<Transacao> transacoes,
        Pessoa pessoa,
        Long version
){
}
