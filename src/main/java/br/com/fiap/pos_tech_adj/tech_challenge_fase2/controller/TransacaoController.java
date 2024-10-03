package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.TransacaoDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private TransacaoService transacaoService;

    @Autowired
    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public ResponseEntity<Page<TransacaoDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "data")Pageable pageable
    ) {
        Page<TransacaoDTO> transacoesDTOS = transacaoService.findAll(pageable);
        return ResponseEntity.ok(transacoesDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransacaoDTO> findById (@PathVariable String id){
        TransacaoDTO transacaoDTO = transacaoService.findById(id);
        return ResponseEntity.ok(transacaoDTO);
    }

    @PostMapping
    public ResponseEntity<TransacaoDTO> save(@Valid @RequestBody TransacaoDTO transacaoDTO){
        TransacaoDTO savedTransacao = transacaoService.save(transacaoDTO);
        return new ResponseEntity<>(savedTransacao, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<TransacaoDTO> update(@PathVariable String id, @Valid @RequestBody TransacaoDTO transacaoDTO){
        TransacaoDTO updatedTransacao = transacaoService.update(id, transacaoDTO);
        return ResponseEntity.ok(updatedTransacao);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        transacaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
