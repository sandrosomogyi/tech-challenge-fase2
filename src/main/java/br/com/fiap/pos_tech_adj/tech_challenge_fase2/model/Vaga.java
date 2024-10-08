package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Vaga {
    @Id
    private String id;

    private String tipoVaga;
    private Integer numVaga;
    private Boolean ocupada;

    @DBRef
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Paquimetro paquimetro;

    @Version
    private Long version;

    public Vaga() {
    }

    public Vaga(String id, String tipoVaga, Integer numVaga, Boolean ocupada, Paquimetro paquimetro, Long version) {
        this.id = id;
        this.tipoVaga = tipoVaga;
        this.numVaga = numVaga;
        this.ocupada = ocupada;
        this.paquimetro = paquimetro;
        this.version = version;
    }
}
