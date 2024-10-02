package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

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

    private Boolean ocupada;

    @DBRef
    private Paquimetro paquimetro;

    @Version
    private Long version;

    public Vaga() {
    }

    public Vaga(String id, String tipoVaga, Boolean ocupada, Paquimetro paquimetro, Long version) {
        this.id = id;
        this.tipoVaga = tipoVaga;
        this.ocupada = ocupada;
        this.paquimetro = paquimetro;
        this.version = version;
    }
}
