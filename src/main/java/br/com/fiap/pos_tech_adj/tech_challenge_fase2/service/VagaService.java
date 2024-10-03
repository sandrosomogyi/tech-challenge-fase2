package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.VagaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.VagaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;

    @Autowired
    public VagaService(VagaRepository vagaRepository){
        this.vagaRepository = vagaRepository;
    }

    public Page<VagaDTO> findAll (Pageable pageable){
        Page<Vaga> vagas = vagaRepository.findAll(pageable);
        return vagas.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public VagaDTO findById(String id){
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));
        return toDTO(vaga);
    }

    @Transactional
    public VagaDTO save(VagaDTO vagaDTO){
        Vaga vaga = vagaRepository.save(toEntity(vagaDTO));
        return toDTO(vaga);
    }

    @Transactional
    public VagaDTO update(String id, VagaDTO vagaDTO){
        try{
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

            vaga.setTipoVaga(vagaDTO.tipoVaga());
            vaga.setOcupada(vagaDTO.ocupada());
            vaga.setPaquimetro(vagaDTO.paquimetro());

            vaga = vagaRepository.save(vaga);
            return toDTO(vaga);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Vaga não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        vagaRepository.deleteById(id);
    }

    private VagaDTO toDTO(Vaga vaga) {
        return new VagaDTO(
                vaga.getId(),
                vaga.getTipoVaga(),
                vaga.getOcupada(),
                vaga.getPaquimetro(),
                vaga.getVersion()
        );
    }

    private Vaga toEntity(VagaDTO vagaDTO) {
        return new Vaga(
                vagaDTO.id(),
                vagaDTO.tipoVaga(),
                vagaDTO.ocupada(),
                vagaDTO.paquimetro(),
                vagaDTO.version()
        );
    }
}
