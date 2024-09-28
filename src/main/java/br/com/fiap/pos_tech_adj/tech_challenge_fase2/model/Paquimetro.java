package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Paquimetro {
    @Id
    private String id;

    @DBRef
    private Endereco endereco;

    private Float valor;

    @DBRef
    private List<Vaga> vagas;

    @Version
    private Long version;

    public Paquimetro() {
    }

    public Paquimetro(String id, Endereco endereco, Float valor, List<Vaga> vagas, Long version) {
        this.id = id;
        this.endereco = endereco;
        this.valor = valor;
        this.vagas = vagas;
        this.version = version;
    }
}
