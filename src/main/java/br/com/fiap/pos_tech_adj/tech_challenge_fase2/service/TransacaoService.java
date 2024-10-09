package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.TransacaoDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Transacao;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.TransacaoRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository){
        this.transacaoRepository = transacaoRepository;
    }

    public Page<TransacaoDTO> findAll (Pageable pageable){
        Page<Transacao> transacaos = transacaoRepository.findAll(pageable);
        return transacaos.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public TransacaoDTO findById(String id){
        Transacao transacao = transacaoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Transação não encontrada"));
        return toDTO(transacao);
    }

    @Transactional
    public TransacaoDTO save(TransacaoDTO transacaoDTO){
        Transacao transacao = transacaoRepository.save(toEntity(transacaoDTO));
        return toDTO(transacao);
    }

    @Transactional
    public TransacaoDTO update(String id, TransacaoDTO transacaoDTO){
        try{
            Transacao transacao = transacaoRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Transação não encontrada"));

            transacao.setMotorista(transacaoDTO.motorista());
            transacao.setCarro(transacaoDTO.carro());
            transacao.setVaga(transacaoDTO.vaga());
            transacao.setData(transacaoDTO.data());
            transacao.setHoras(transacaoDTO.horas());

            transacao = transacaoRepository.save(transacao);
            return toDTO(transacao);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Transação não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        transacaoRepository.deleteById(id);
    }

    private TransacaoDTO toDTO(Transacao transacao) {
        return new TransacaoDTO(
                transacao.getId(),
                transacao.getMotorista(),
                transacao.getCarro(),
                transacao.getVaga(),
                transacao.getData(),
                transacao.getHoras(),
                transacao.getVersion()
        );
    }

    private Transacao toEntity(TransacaoDTO transacaoDTO) {
        return new Transacao(
                transacaoDTO.id(),
                transacaoDTO.motorista(),
                transacaoDTO.carro(),
                transacaoDTO.vaga(),
                transacaoDTO.data(),
                transacaoDTO.horas(),
                transacaoDTO.dataExpiracao(),
                transacaoDTO.version()
        );
    }
}
