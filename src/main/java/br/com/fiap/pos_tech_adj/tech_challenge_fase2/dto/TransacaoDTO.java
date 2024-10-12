package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TransacaoDTO (
        String id,

        @NotBlank(message = "Motorista não pode estar em branco.")
        String idMotorista,

        @NotBlank(message = "Carro não pode estar em branco.")
        String idCarro,

        @NotBlank(message = "Vaga não pode estar em branco.")
        String idVaga,

        LocalDateTime dataHora,

        @NotNull(message = "Horas não pode estar em branco.")
        Integer horas,

        LocalDateTime dataExpiracao,

        Long version
){
        
    public TransacaoDTO(
            String id,
            String idMotorista,
            String idCarro,
            String idVaga,
            Integer horas,
            Long version
    ) {
        this(id, idMotorista, idCarro, idVaga, LocalDateTime.now(), horas, LocalDateTime.now().plusHours(horas), version);
    }        
}
