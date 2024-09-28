package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Endereco {
    @Id
    private String id;

    private String uf;
    private String cidade;
    private String bairro;
    private String rua;
    private Integer numero;
    private String complemento;

    @Version
    private Long version;

    public Endereco() {
    }

    public Endereco(String id, String uf, String cidade, String bairro, String rua, Integer numero, String complemento, Long version) {
        this.id = id;
        this.uf = uf;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.version = version;
    }
}