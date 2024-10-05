package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Endereco;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record PaquimetroDTO (
        String id,

        @NotBlank(message = "Endereço não pode estar em branco.")
        Endereco endereco,

        @NotBlank(message = "Valor não pode estar em branco.")
        Float valor,

        List<Vaga>vagas,
        Long version
){
}
