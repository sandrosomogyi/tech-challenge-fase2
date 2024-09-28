package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class AgenteFiscal {

    @Id
    private String id;

    @DBRef
    private Pessoa pessoa;

    @Version
    private Long version;

    // Contrutor Padrão
    public AgenteFiscal() {
    }

    public AgenteFiscal(String id, Pessoa pessoa, Long version) {
        this.id = id;
        this.pessoa = pessoa;
        this.version = version;
    }
}
