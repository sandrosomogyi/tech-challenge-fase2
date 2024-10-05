package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record TransacaoDTO (
        String id,

        @NotBlank(message = "Motorista não pode estar em branco.")
        Motorista motorista,

        @NotBlank(message = "Carro não pode estar em branco.")
        Carro carro,

        @NotBlank(message = "Vaga não pode estar em branco.")
        Vaga vaga,

        @NotBlank(message = "Data não pode estar em branco.")
        LocalDate data,

        @NotBlank(message = "Horas não pode estar em branco.")
        Integer horas,

        Long version
){
}
