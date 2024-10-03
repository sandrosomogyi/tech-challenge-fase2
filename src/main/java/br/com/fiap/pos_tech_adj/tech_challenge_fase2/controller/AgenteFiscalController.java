package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.AgenteFiscalDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.AgenteFiscalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenteFiscal")
public class AgenteFiscalController {

    private AgenteFiscalService agenteFiscalService;

    @Autowired
    public AgenteFiscalController(AgenteFiscalService agenteFiscalService){
        this.agenteFiscalService = agenteFiscalService;
    }

    @GetMapping
    public ResponseEntity<Page<AgenteFiscalDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "id")Pageable pageable
    ) {
        Page<AgenteFiscalDTO> agenteFiscalDTOS = agenteFiscalService.findAll(pageable);
        return ResponseEntity.ok(agenteFiscalDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgenteFiscalDTO> findById (@PathVariable String id){
        AgenteFiscalDTO agenteFiscalDTO = agenteFiscalService.findById(id);
        return ResponseEntity.ok(agenteFiscalDTO);
    }

    @PostMapping
    public ResponseEntity<AgenteFiscalDTO> save(@Valid @RequestBody AgenteFiscalDTO agenteFiscalDTO){
        AgenteFiscalDTO savedAgenteFiscal = agenteFiscalService.save(agenteFiscalDTO);
        return new ResponseEntity<>(savedAgenteFiscal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<AgenteFiscalDTO> update(@PathVariable String id, @Valid @RequestBody AgenteFiscalDTO agenteFiscalDTO){
        AgenteFiscalDTO updatedAgenteFiscal = agenteFiscalService.update(id, agenteFiscalDTO);
        return ResponseEntity.ok(updatedAgenteFiscal);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        agenteFiscalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
