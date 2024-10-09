package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document
public class Transacao {
    @Id
    private String id;

    @DBRef
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")  // Usar o campo `id` como referência
    @JsonIdentityReference(alwaysAsId = true)  // Mostrar apenas o ID na serialização
    private Motorista motorista;

    @DBRef
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Carro carro;

    @DBRef
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Vaga vaga;

    private LocalDateTime dataHora;
    private Integer horas;

    @Version
    private Long version;

    public Transacao() {
    }

    public Transacao(String id, Motorista motorista, Carro carro, Vaga vaga, LocalDateTime dataHora, Integer horas, Long version) {
        this.id = id;
        this.motorista = motorista;
        this.carro = carro;
        this.vaga = vaga;
        this.dataHora = dataHora;
        this.horas = horas;
        this.version = version;
    }
}
