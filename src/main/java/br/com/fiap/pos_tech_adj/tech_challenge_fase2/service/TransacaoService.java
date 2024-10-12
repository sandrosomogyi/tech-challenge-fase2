package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.TransacaoDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.*;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.CarroRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.MotoristaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.TransacaoRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.VagaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final MotoristaRepository motoristaRepository;
    private final CarroRepository carroRepository;
    private final VagaRepository vagaRepository;

    @Autowired
    public TransacaoService(TransacaoRepository transacaoRepository, MotoristaRepository motoristaRepository, CarroRepository carroRepository,
                            VagaRepository vagaRepository){
        this.transacaoRepository = transacaoRepository;
        this.motoristaRepository = motoristaRepository;
        this.carroRepository = carroRepository;
        this.vagaRepository = vagaRepository;
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

            Motorista motorista = motoristaRepository.findById(transacaoDTO.idMotorista())
                    .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

            Carro carro = carroRepository.findById(transacaoDTO.idCarro())
                    .orElseThrow(() -> new ControllerNotFoundException("Carro não encontrada"));

            Vaga vaga = vagaRepository.findById(transacaoDTO.idVaga())
                    .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

            transacao.setMotorista(motorista);
            transacao.setCarro(carro);
            transacao.setVaga(vaga);
            transacao.setDataHora(transacaoDTO.dataHora());
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

    @Transactional
    public TransacaoDTO ocuparVaga(TransacaoDTO transacaoDTO){
        Motorista motorista = motoristaRepository.findById(transacaoDTO.idMotorista())
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

        Vaga vaga = vagaRepository.findById(transacaoDTO.idVaga())
                .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

        var valor = vaga.getPaquimetro().getValor() * transacaoDTO.horas();

        if (motorista.getSaldo() <= valor){
            throw new ControllerNotFoundException("Motorista sem Saldo suficiente!");
        }
        if (vaga.getOcupada()){
            throw new ControllerNotFoundException("Vaga já está Ocupada!");
        }
  
        TransacaoDTO novaTransacaoDTO = new TransacaoDTO(
                transacaoDTO.id(),
                transacaoDTO.idMotorista(),
                transacaoDTO.idCarro(),
                transacaoDTO.idVaga(),
                transacaoDTO.horas(),
                transacaoDTO.version()
        );

        Transacao transacao = transacaoRepository.save(toEntity(novaTransacaoDTO));

        vaga.setOcupada(true);
        vagaRepository.save(vaga);

        motorista.setSaldo(motorista.getSaldo() - valor);
        motorista.getTransacoes().add(transacao);
        motoristaRepository.save(motorista);

        return toDTO(transacao);
    }

    private TransacaoDTO toDTO(Transacao transacao) {


        return new TransacaoDTO(
                transacao.getId(),
                transacao.getMotorista().getId(),
                transacao.getCarro().getId(),
                transacao.getVaga().getId(),
                transacao.getDataHora(),
                transacao.getHoras(),
                transacao.getDataExpiracao(),
                transacao.getVersion()
        );
    }

    private Transacao toEntity(TransacaoDTO transacaoDTO) {
        Motorista motorista = motoristaRepository.findById(transacaoDTO.idMotorista())
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

        Carro carro = carroRepository.findById(transacaoDTO.idCarro())
                .orElseThrow(() -> new ControllerNotFoundException("Carro não encontrada"));

        Vaga vaga = vagaRepository.findById(transacaoDTO.idVaga())
                .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

        return new Transacao(
                transacaoDTO.id(),
                motorista,
                carro,
                vaga,
                transacaoDTO.dataHora(),
                transacaoDTO.horas(),
                transacaoDTO.dataExpiracao(),
                transacaoDTO.version()
        );
    }
}
