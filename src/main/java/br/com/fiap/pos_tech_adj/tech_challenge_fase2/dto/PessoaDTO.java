package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

import jakarta.validation.constraints.NotBlank;

public record PessoaDTO(
        String id,

        @NotBlank(message = "E-mail não pode estar em branco.")
        String email,

        @NotBlank(message = "Nome não pode estar em branco.")
        String nome,

        @NotBlank(message = "Sobrenome não pode estar em branco.")
        String sobrenome,

        String telefone,

        @NotBlank(message = "Senha não pode estar em branco.")
        String senha,

        Long version) {
}
