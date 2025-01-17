package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import jakarta.validation.constraints.NotBlank;

public record CarroDTO (
        String id,

        @NotBlank(message = "Placa não pode estar em branco.")
        String placa,

        String marca,
        String modelo,
        String cor,

        @NotBlank(message = "idMotorista não pode estar em branco.")
        String idMotorista,

        Long version
){
}
