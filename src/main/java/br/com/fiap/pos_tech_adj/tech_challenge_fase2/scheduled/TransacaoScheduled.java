package br.com.fiap.pos_tech_adj.tech_challenge_fase2.scheduled;



import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.TransacaoRepository;

@Component
public class TransacaoScheduled {

    private final TransacaoRepository transacaoRepository;

    public TransacaoScheduled(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }
    
    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto (60000 milissegundos)
    public void verificarExpiracao(){

        System.out.println("Verificando transações expiradas...");
    }
}
