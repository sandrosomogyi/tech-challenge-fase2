package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Carro {
    @Id
    private String id;

    private String Placa;
    private String Marca;
    private String Modelo;
    private String Cor;

    @Version
    private Long version;

    public Carro() {
    }

    public Carro(String id, String placa, String marca, String modelo, String cor, Long version) {
        this.id = id;
        Placa = placa;
        Marca = marca;
        Modelo = modelo;
        Cor = cor;
        this.version = version;
    }
}
