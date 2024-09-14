package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Pessoa {

    @Id
    private String id;
    private String email;
    private String nome;
    private String sobrenome;
    private String telefone;

    @Version
    private Long version;

    // Contrutor Padrão
    public Pessoa() {
    }

    public Pessoa(String id, String email, String nome, String sobrenome, String telefone, Long version) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.version = version;
    }
}
