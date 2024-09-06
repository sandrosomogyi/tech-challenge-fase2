package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.PessoaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

    private PessoaService pessoaService;

    @Autowired
    public PessoaController (PessoaService pessoaService){
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<Page<PessoaDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "email")Pageable pageable
    ) {
        Page<PessoaDTO> pessoaDTOS = pessoaService.findAll(pageable);
        return ResponseEntity.ok(pessoaDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PessoaDTO> findById (@PathVariable String id){
        PessoaDTO pessoaDTO = pessoaService.findById(id);
        return ResponseEntity.ok(pessoaDTO);
    }

    @PostMapping
    public ResponseEntity<PessoaDTO> save(@RequestBody PessoaDTO pessoaDTO){
        PessoaDTO savedPessoa = pessoaService.save(pessoaDTO);
        return new ResponseEntity<>(savedPessoa, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<PessoaDTO> update(@PathVariable String id, @RequestBody PessoaDTO pessoaDTO){
        PessoaDTO updatedPessoa = pessoaService.update(id, pessoaDTO);
        return ResponseEntity.ok(updatedPessoa);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
