package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.PessoaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.entities.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PessoaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    @Autowired
    public PessoaService (PessoaRepository pessoaRepository){
        this.pessoaRepository = pessoaRepository;
    }

    public Page<PessoaDTO> findAll (Pageable pageable){
        Page<Pessoa> pessoas = pessoaRepository.findAll(pageable);
        return pessoas.map(this::toDTO);
    }

    public PessoaDTO findById(String id){
        Pessoa Pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));
        return toDTO(Pessoa);
    }

    public PessoaDTO save(PessoaDTO pessoaDTO){
        Pessoa pessoa = pessoaRepository.save(toEntity(pessoaDTO));
        return toDTO(pessoa);
    }

    public PessoaDTO update(String id, PessoaDTO pessoaDTO){
        try{
            Pessoa pessoa = pessoaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));

            pessoa.setNome(pessoaDTO.nome());
            pessoa.setSobrenome(pessoaDTO.sobrenome());
            pessoa.setTelefone(pessoaDTO.telefone());
            pessoa.setEmail(pessoaDTO.email());

            pessoa = pessoaRepository.save(pessoa);
            return toDTO(pessoa);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Pessoa não encontrada");
        }
    }

    public void delete(String id){
        pessoaRepository.deleteById(id);
    }

    private PessoaDTO toDTO(Pessoa pessoa) {
        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getNome(),
                pessoa.getSobrenome(),
                pessoa.getTelefone()
        );
    }

    private Pessoa toEntity(PessoaDTO pessoaDTO) {
        return new Pessoa(
                pessoaDTO.id(),
                pessoaDTO.email(),
                pessoaDTO.nome(),
                pessoaDTO.sobrenome(),
                pessoaDTO.telefone()
        );
    }
}