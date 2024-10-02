package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.EnderecoDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Endereco;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.EnderecoRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository){
        this.enderecoRepository = enderecoRepository;
    }

    public Page<EnderecoDTO> findAll (Pageable pageable){
        Page<Endereco> enderecos = enderecoRepository.findAll(pageable);
        return enderecos.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public EnderecoDTO findById(String id){
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Endereco não encontrado"));
        return toDTO(endereco);
    }

    @Transactional
    public EnderecoDTO save(EnderecoDTO EnderecoDTO){
        Endereco endereco = enderecoRepository.save(toEntity(EnderecoDTO));
        return toDTO(endereco);
    }

    @Transactional
    public EnderecoDTO update(String id, EnderecoDTO enderecoDTO){
        try{
            Endereco endereco = enderecoRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Endereco não encontrado"));

            endereco.setUf(enderecoDTO.uf());
            endereco.setCidade(enderecoDTO.cidade());
            endereco.setBairro(enderecoDTO.bairro());
            endereco.setRua(enderecoDTO.rua());
            endereco.setNumero(enderecoDTO.numero());
            endereco.setComplemento(enderecoDTO.complemento());

            endereco = enderecoRepository.save(endereco);
            return toDTO(endereco);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Endereco não encontrado");
        }
    }

    @Transactional
    public void delete(String id){
        enderecoRepository.deleteById(id);
    }

    private EnderecoDTO toDTO(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getId(),
                endereco.getUf(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getVersion()
        );
    }

    private Endereco toEntity(EnderecoDTO enderecoDTO) {
        return new Endereco(
                enderecoDTO.id(),
                enderecoDTO.uf(),
                enderecoDTO.cidade(),
                enderecoDTO.bairro(),
                enderecoDTO.rua(),
                enderecoDTO.numero(),
                enderecoDTO.complemento(),
                enderecoDTO.version()
        );
    }
}
