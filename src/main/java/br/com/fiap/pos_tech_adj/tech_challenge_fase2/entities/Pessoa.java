package br.com.fiap.pos_tech_adj.tech_challenge_fase2.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
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

    // Contrutor Padrão
    public Pessoa() {
    }

    public Pessoa(String id, String email, String nome, String sobrenome, String telefone) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
    }
}
