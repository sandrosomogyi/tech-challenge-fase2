package br.com.fiap.pos_tech_adj.tech_challenge_fase2.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Motorista {

    @Id
    private String id;

    private Float saldo;

    @DBRef
    private List<Carro> carros;

    @DBRef
    private List<Transacao> transacoes;

    @DBRef
    @Indexed(unique = true)  // Definindo o campo `pessoa` como único na base de dados
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
