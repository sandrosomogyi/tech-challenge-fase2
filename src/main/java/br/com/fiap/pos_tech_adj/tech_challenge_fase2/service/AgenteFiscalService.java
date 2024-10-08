package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.AgenteFiscalDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.AgenteFiscal;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Pessoa;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.AgenteFiscalRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.PessoaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AgenteFiscalService {

    private final AgenteFiscalRepository agenteFiscalRepository;
    private final PessoaRepository pessoaRepository;

    @Autowired
    public AgenteFiscalService(AgenteFiscalRepository agenteFiscalRepository,  PessoaRepository pessoaRepository){
        this.agenteFiscalRepository = agenteFiscalRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public Page<AgenteFiscalDTO> findAll (Pageable pageable){
        Page<AgenteFiscal> agentesFiscais = agenteFiscalRepository.findAll(pageable);
        return agentesFiscais.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public AgenteFiscalDTO findById(String id){
        AgenteFiscal agenteFiscal = agenteFiscalRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("AgenteFiscal não encontrada"));
        return toDTO(agenteFiscal);
    }

    @Transactional
    public AgenteFiscalDTO save(AgenteFiscalDTO agenteFiscalDTO){
        AgenteFiscal agenteFiscal = agenteFiscalRepository.save(toEntity(agenteFiscalDTO));
        return toDTO(agenteFiscal);
    }

    @Transactional
    public AgenteFiscalDTO update(String id, AgenteFiscalDTO agenteFiscalDTO){
        try{
            AgenteFiscal agenteFiscal = agenteFiscalRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("AgenteFiscal não encontrada"));

            agenteFiscal.setPessoa(agenteFiscalDTO.pessoa());
            agenteFiscal.setVersion(agenteFiscalDTO.version());

            agenteFiscal = agenteFiscalRepository.save(agenteFiscal);
            return toDTO(agenteFiscal);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("AgenteFiscal não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        agenteFiscalRepository.deleteById(id);
    }

    @Transactional(readOnly = true )
    public AgenteFiscalDTO loginAgente(String email, String senha){
        try {
            Pessoa pessoa = pessoaRepository.findByEmailAndSenha(email, senha)
                    .orElseThrow(() -> new ControllerNotFoundException("Não foi possivel realizar o Login verifique os dados."));;

            AgenteFiscal agenteFiscal = agenteFiscalRepository.findByPessoa_Id(pessoa.getId())
                    .orElseThrow(() -> new ControllerNotFoundException("Agente Fiscal não encontrada"));;

            return toDTO(agenteFiscal);
        }catch (MongoCursorNotFoundException e) {
            throw new ControllerNotFoundException("Não foi possivel realizar o Login verifique os dados.");
        }
    }

    private AgenteFiscalDTO toDTO(AgenteFiscal agenteFiscal) {
        return new AgenteFiscalDTO(
                agenteFiscal.getId(),
                agenteFiscal.getPessoa(),
                agenteFiscal.getVersion()
        );
    }

    private AgenteFiscal toEntity(AgenteFiscalDTO agenteFiscalDTO) {
        Pessoa pessoa = pessoaRepository.findById(agenteFiscalDTO.pessoa().getId())
                .orElseThrow(() -> new ControllerNotFoundException("Pessoa não encontrada"));

        return new AgenteFiscal(
                agenteFiscalDTO.id(),
                pessoa,
                agenteFiscalDTO.version()
        );
    }
}
