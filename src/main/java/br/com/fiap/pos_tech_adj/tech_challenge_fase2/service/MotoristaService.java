package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.MotoristaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.MotoristaRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PessoaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotoristaService {


    private final MotoristaRepository motoristaRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository, PessoaRepository pessoaRepository){
        this.motoristaRepository = motoristaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Page<MotoristaDTO> findAll (Pageable pageable){
        Page<Motorista> motoristas = motoristaRepository.findAll(pageable);
        return motoristas.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public MotoristaDTO findById(String id){
        Motorista motorista = motoristaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));
        return toDTO(motorista);
    }

    @Transactional
    public MotoristaDTO save(MotoristaDTO motoristaDTO){
        Motorista motorista = motoristaRepository.save(toEntity(motoristaDTO));
        return toDTO(motorista);
    }

    @Transactional
    public MotoristaDTO update(String id, MotoristaDTO motoristaDTO){
        try{
            Motorista motorista = motoristaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

            motorista.setSaldo(motoristaDTO.saldo());

            motorista.setCarros(motoristaDTO.carros());
            motorista.setTransacoes(motoristaDTO.transacoes());
            motorista.setPessoa(motoristaDTO.pessoa());

            motorista = motoristaRepository.save(motorista);
            return toDTO(motorista);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Motorista não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        motoristaRepository.deleteById(id);
    }

    @Transactional
    public MotoristaDTO recarregarSaldo(String id, Float saldo){
        try{
            Motorista motorista = motoristaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

            motorista.setSaldo(motorista.getSaldo() + saldo);

            motorista = motoristaRepository.save(motorista);
            return toDTO(motorista);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Motorista não encontrada");
        }
    }

    @Transactional(readOnly = true )
    public MotoristaDTO loginMotorista(String email, String senha){
        try {
            Pessoa pessoa = pessoaRepository.findByEmailAndSenha(email, senha)
                    .orElseThrow(() -> new ControllerNotFoundException("Não foi possivel realizar o Login verifique os dados."));;

            Motorista motorista = motoristaRepository.findByPessoa_Id(pessoa.getId())
                    .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));;

            return toDTO(motorista);
        }catch (MongoCursorNotFoundException e) {
            throw new ControllerNotFoundException("Não foi possivel realizar o Login verifique os dados.");
        }
    }

    private MotoristaDTO toDTO(Motorista motorista) {
        return new MotoristaDTO(
                motorista.getId(),
                motorista.getSaldo(),
                motorista.getCarros(),
                motorista.getTransacoes(),
                motorista.getPessoa(),
                motorista.getVersion()
        );
    }

    private Motorista toEntity(MotoristaDTO motoristaDTO) {

        Pessoa pessoa = pessoaRepository.findById(motoristaDTO.pessoa().getId())
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));

        return new Motorista(
                motoristaDTO.id(),
                motoristaDTO.saldo(),
                motoristaDTO.carros(),
                motoristaDTO.transacoes(),
                pessoa,
                motoristaDTO.version()
        );
    }
}
