package br.com.fiap.pos_tech_adj.tech_challenge_fase2.scheduled;



import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.time.LocalDateTime;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Transacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.TransacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.VagaRepository;

@Component
public class TransacaoScheduled {

    private final TransacaoRepository transacaoRepository;
    private final VagaRepository vagaRepository;

    public TransacaoScheduled(TransacaoRepository transacaoRepository, VagaRepository vagaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.vagaRepository = vagaRepository;
    }
    
    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto (60000 milissegundos)
    public void verificarExpiracao(){
        System.out.println("Verificando transações expiradas para desocupar vagas...");

        List<Vaga> vagasOcupadas = vagaRepository.findByOcupada(true);
        
        for (Vaga vaga : vagasOcupadas) {
           Transacao transacao = transacaoRepository.findByVaga(vaga.getId());
           if (transacao != null && transacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
                vaga.setOcupada(false);
                vagaRepository.save(vaga);
                System.out.println("Vaga " + vaga.getId() + " desocupada.");
           }
        }       
    }
}
