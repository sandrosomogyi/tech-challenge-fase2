package br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto;

public record EnderecoDTO(
        String id,
        String uf,
        String cidade,
        String bairro,
        String rua,
        Integer numero,
        String complemento,
        Long version
) {
}
