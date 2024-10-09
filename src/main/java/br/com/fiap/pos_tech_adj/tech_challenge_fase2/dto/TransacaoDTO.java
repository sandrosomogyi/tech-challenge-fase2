package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransacaoDTO (
        String id,

        @NotBlank(message = "Motorista não pode estar em branco.")
        Motorista motorista,

        @NotBlank(message = "Carro não pode estar em branco.")
        Carro carro,

        @NotBlank(message = "Vaga não pode estar em branco.")
        Vaga vaga,

        @NotBlank(message = "Data não pode estar em branco.")
        LocalDateTime data,

        @NotNull(message = "Horas não pode estar em branco.")
        Integer horas,

        LocalDateTime dataExpiracao,

        Long version
) {
        public TransacaoDTO(
                String id,
                Motorista motorista,
                Carro carro,
                Vaga vaga,
                LocalDateTime data,
                Integer horas,
                Long version
        ) {
                this(id, motorista, carro, vaga, data, horas, data.plusHours(horas), version);
        }
}
