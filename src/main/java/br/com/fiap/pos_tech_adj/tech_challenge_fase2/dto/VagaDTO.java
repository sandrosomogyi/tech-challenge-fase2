package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VagaDTO (
        String id,

        @NotBlank(message = "Tipo Vaga não pode estar em branco.")
        String tipoVaga,

        @NotNull(message = "Numero da Vaga não pode estar em branco.")
        Integer numVaga,
        Boolean ocupada,

        @NotBlank(message = "Paquimetro não pode estar em branco.")
        String idPaquimetro,
        Long version
) {
}
