package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.VagaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Paquimetro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Vaga;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PaquimetroRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.VagaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class VagaService {

    private final VagaRepository vagaRepository;
    private  final PaquimetroRepository paquimetroRepository;

    @Autowired
    public VagaService(VagaRepository vagaRepository, PaquimetroRepository paquimetroRepository){
        this.vagaRepository = vagaRepository;
        this.paquimetroRepository = paquimetroRepository;
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

        vaga.getPaquimetro().getVagas().add(vaga);

        paquimetroRepository.save(vaga.getPaquimetro());

        return toDTO(vaga);
    }

    @Transactional
    public VagaDTO update(String id, VagaDTO vagaDTO){
        try{
            Vaga vaga = vagaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

            vaga.setTipoVaga(vagaDTO.tipoVaga());
            vaga.setOcupada(vagaDTO.ocupada());

            Paquimetro paquimetro = paquimetroRepository.findById(vagaDTO.idPaquimetro())
                    .orElseThrow(() -> new ControllerNotFoundException("Paquimetro não encontrada"));

            paquimetro.getVagas().removeIf(x -> Objects.equals(x.getId(), id));;
            paquimetro.getVagas().add(vaga);

            paquimetro.setVagas(vagas);

            paquimetro = paquimetroRepository.save(paquimetro);

            vaga.setPaquimetro(paquimetro);

            vaga = vagaRepository.save(vaga);
            return toDTO(vaga);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Vaga não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Vaga não encontrada"));

        Paquimetro paquimetro = paquimetroRepository.findById(vaga.getPaquimetro().getId())
                .orElseThrow(() -> new ControllerNotFoundException("Paquimetro não encontrada"));

        paquimetro.getVagas().removeIf(x -> Objects.equals(x.getId(), id));

        paquimetroRepository.save(paquimetro);

        vagaRepository.deleteById(id);
    }

    private VagaDTO toDTO(Vaga vaga) {
        return new VagaDTO(
                vaga.getId(),
                vaga.getTipoVaga(),
                vaga.getNumVaga(),
                vaga.getOcupada(),
                vaga.getPaquimetro().getId(),
                vaga.getVersion()
        );
    }

    private Vaga toEntity(VagaDTO vagaDTO) {
        Paquimetro paquimetro = paquimetroRepository.findById(vagaDTO.idPaquimetro())
                .orElseThrow(() -> new ControllerNotFoundException("Paquimetro não encontrada"));

        return new Vaga(
                vagaDTO.id(),
                vagaDTO.tipoVaga(),
                vagaDTO.numVaga(),
                vagaDTO.ocupada(),
                paquimetro,
                vagaDTO.version()
        );
    }
}
