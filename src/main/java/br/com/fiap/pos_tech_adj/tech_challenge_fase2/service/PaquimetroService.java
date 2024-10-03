package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.PaquimetroDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Paquimetro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PaquimetroRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaquimetroService {

    private final PaquimetroRepository paquimetroRepository;

    @Autowired
    public PaquimetroService(PaquimetroRepository paquimetroRepository){
        this.paquimetroRepository = paquimetroRepository;
    }

    public Page<PaquimetroDTO> findAll (Pageable pageable){
        Page<Paquimetro> paquimetros = paquimetroRepository.findAll(pageable);
        return paquimetros.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public PaquimetroDTO findById(String id){
        Paquimetro paquimetro = paquimetroRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Paquimetro não encontrada"));
        return toDTO(paquimetro);
    }

    @Transactional
    public PaquimetroDTO save(PaquimetroDTO paquimetroDTO){
        Paquimetro paquimetro = paquimetroRepository.save(toEntity(paquimetroDTO));
        return toDTO(paquimetro);
    }

    @Transactional
    public PaquimetroDTO update(String id, PaquimetroDTO paquimetroDTO){
        try{
            Paquimetro paquimetro = paquimetroRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Paquimetro não encontrada"));

            paquimetro.setEndereco(paquimetroDTO.endereco());
            paquimetro.setValor(paquimetroDTO.valor());
            paquimetro.setVagas(paquimetroDTO.vagas());

            paquimetro = paquimetroRepository.save(paquimetro);
            return toDTO(paquimetro);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Paquimetro não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        paquimetroRepository.deleteById(id);
    }

    private PaquimetroDTO toDTO(Paquimetro paquimetro) {
        return new PaquimetroDTO(
                paquimetro.getId(),
                paquimetro.getEndereco(),
                paquimetro.getValor(),
                paquimetro.getVagas(),
                paquimetro.getVersion()
        );
    }

    private Paquimetro toEntity(PaquimetroDTO paquimetroDTO) {
        return new Paquimetro(
                paquimetroDTO.id(),
                paquimetroDTO.endereco(),
                paquimetroDTO.valor(),
                paquimetroDTO.vagas(),
                paquimetroDTO.version()
        );
    }
}
