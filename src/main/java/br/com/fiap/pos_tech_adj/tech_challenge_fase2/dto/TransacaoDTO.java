package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransacaoDTO (
        String id,

        @NotBlank(message = "Motorista não pode estar em branco.")
        String idMotorista,

        @NotBlank(message = "Carro não pode estar em branco.")
        String idCarro,

        @NotBlank(message = "Vaga não pode estar em branco.")
        String idVaga,

        @NotNull(message = "Data Hora não pode estar em branco.")
        LocalDateTime dataHora,

        @NotNull(message = "Horas não pode estar em branco.")
        Integer horas,

        Long version
){
}
