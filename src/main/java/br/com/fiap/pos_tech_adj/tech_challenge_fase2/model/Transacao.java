package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document
public class Transacao {
    @Id
    private String id;

    @DBRef
    private Motorista motorista;

    @DBRef
    private Carro carro;

    @DBRef
    private Vaga vaga;

    private LocalDate data;
    private Integer horas;

    @Version
    private Long version;

    public Transacao() {
    }

    public Transacao(String id, Motorista motorista, Carro carro, Vaga vaga, LocalDate data, Integer horas, Long version) {
        this.id = id;
        this.motorista = motorista;
        this.carro = carro;
        this.vaga = vaga;
        this.data = data;
        this.horas = horas;
        this.version = version;
    }
}
