package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.MotoristaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.MotoristaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;

    @Autowired
    public MotoristaService(MotoristaRepository motoristaRepository){
        this.motoristaRepository = motoristaRepository;
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
        return new Motorista(
                motoristaDTO.id(),
                motoristaDTO.saldo(),
                motoristaDTO.carros(),
                motoristaDTO.transacoes(),
                motoristaDTO.pessoa(),
                motoristaDTO.version()
        );
    }
}
