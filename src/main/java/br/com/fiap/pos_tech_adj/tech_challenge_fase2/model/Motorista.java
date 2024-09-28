package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Motorista {

    @Id
    private String id;

    private Float saldo;

    @DBRef
    private List<Carro> carros;

    @DBRef
    private List<Transacao> transacoes;

    @DBRef
    private Pessoa pessoa;

    @Version
    private Long version;

    // Contrutor Padrão
    public Motorista() {
    }

    public Motorista(String id, Float saldo, List<Carro> carros, List<Transacao> transacoes, Pessoa pessoa, Long version) {
        this.id = id;
        this.saldo = saldo;
        this.carros = carros;
        this.transacoes = transacoes;
        this.pessoa = pessoa;
        this.version = version;
    }
}
