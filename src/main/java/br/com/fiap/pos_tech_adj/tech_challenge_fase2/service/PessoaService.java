package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.PessoaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PessoaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(readOnly = true )
    public PessoaDTO findById(String id){
        Pessoa Pessoa = pessoaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));
        return toDTO(Pessoa);
    }

    @Transactional
    public PessoaDTO save(PessoaDTO pessoaDTO){
        Pessoa pessoa = pessoaRepository.save(toEntity(pessoaDTO));
        return toDTO(pessoa);
    }

    @Transactional
    public PessoaDTO update(String id, PessoaDTO pessoaDTO){
        try{
            Pessoa pessoa = pessoaRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));

            pessoa.setNome(pessoaDTO.nome());
            pessoa.setSobrenome(pessoaDTO.sobrenome());
            pessoa.setTelefone(pessoaDTO.telefone());
            pessoa.setEmail(pessoaDTO.email());
            pessoa.setSenha(pessoaDTO.senha());

            pessoa = pessoaRepository.save(pessoa);
            return toDTO(pessoa);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Pessoa não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        pessoaRepository.deleteById(id);
    }

    private PessoaDTO toDTO(Pessoa pessoa) {
        return new PessoaDTO(
                pessoa.getId(),
                pessoa.getEmail(),
                pessoa.getNome(),
                pessoa.getSobrenome(),
                pessoa.getCpf(),
                pessoa.getTelefone(),
                pessoa.getSenha(),
                pessoa.getVersion()
        );
    }

    private Pessoa toEntity(PessoaDTO pessoaDTO) {
        return new Pessoa(
                pessoaDTO.id(),
                pessoaDTO.email(),
                pessoaDTO.nome(),
                pessoaDTO.sobrenome(),
                pessoaDTO.cpf(),
                pessoaDTO.telefone(),
                pessoaDTO.senha(),
                pessoaDTO.version()
        );
    }
}
