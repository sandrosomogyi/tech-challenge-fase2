package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.EnderecoDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Endereco")
public class EnderecoController {

    private EnderecoService enderecoService;

    @Autowired
    public EnderecoController(EnderecoService enderecoService){
        this.enderecoService = enderecoService;
    }

    @GetMapping
    public ResponseEntity<Page<EnderecoDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "uf")Pageable pageable
    ) {
        Page<EnderecoDTO> enderecosDTOS = enderecoService.findAll(pageable);
        return ResponseEntity.ok(enderecosDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoDTO> findById (@PathVariable String id){
        EnderecoDTO enderecoDTO = enderecoService.findById(id);
        return ResponseEntity.ok(enderecoDTO);
    }

    @PostMapping
    public ResponseEntity<EnderecoDTO> save(@Valid @RequestBody EnderecoDTO enderecoDTO){
        EnderecoDTO savedEndereco = enderecoService.save(enderecoDTO);
        return new ResponseEntity<>(savedEndereco, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<EnderecoDTO> update(@PathVariable String id, @Valid @RequestBody EnderecoDTO enderecoDTO){
        EnderecoDTO updatedEndereco = enderecoService.update(id, enderecoDTO);
        return ResponseEntity.ok(updatedEndereco);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
