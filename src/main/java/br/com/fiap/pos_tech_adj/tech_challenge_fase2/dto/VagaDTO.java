package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Paquimetro;

public record VagaDTO (
        String id,
        String tipoVaga,
        Boolean ocupada,
        Paquimetro paquimetro,
        Long version
) {
}
