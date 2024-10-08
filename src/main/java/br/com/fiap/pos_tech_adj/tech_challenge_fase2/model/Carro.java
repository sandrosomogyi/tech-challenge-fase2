package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Carro {
    @Id
    private String id;

    private String placa;
    private String marca;
    private String modelo;
    private String cor;

    @DBRef
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Motorista motorista;

    @Version
    private Long version;

    public Carro() {
    }

    public Carro(String id, String placa, String marca, String modelo, String cor, Motorista motorista, Long version) {
        this.id = id;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.cor = cor;
        this.motorista = motorista;
        this.version = version;
    }
}
