package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(
        String id,

        @NotBlank(message = "UF não pode estar em branco.")
        String uf,

        @NotBlank(message = "Cidade não pode estar em branco.")
        String cidade,

        @NotBlank(message = "Bairro não pode estar em branco.")
        String bairro,

        @NotBlank(message = "Rua não pode estar em branco.")
        String rua,

        @NotBlank(message = "Número não pode estar em branco.")
        Integer numero,

        String complemento,
        String cep,

        Long version
) {
}
