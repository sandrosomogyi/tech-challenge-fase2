package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.VagaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.VagaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vaga")
public class VagaController {

    private VagaService vagaService;

    @Autowired
    public VagaController(VagaService vagaService){
        this.vagaService = vagaService;
    }

    @GetMapping
    public ResponseEntity<Page<VagaDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "ocupada")Pageable pageable
    ) {
        Page<VagaDTO> vagasDTOS = vagaService.findAll(pageable);
        return ResponseEntity.ok(vagasDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VagaDTO> findById (@PathVariable String id){
        VagaDTO vagaDTO = vagaService.findById(id);
        return ResponseEntity.ok(vagaDTO);
    }

    @PostMapping
    public ResponseEntity<VagaDTO> save(@Valid @RequestBody VagaDTO vagaDTO){
        VagaDTO savedVaga = vagaService.save(vagaDTO);
        return new ResponseEntity<>(savedVaga, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<VagaDTO> update(@PathVariable String id, @Valid @RequestBody VagaDTO vagaDTO){
        VagaDTO updatedVaga = vagaService.update(id, vagaDTO);
        return ResponseEntity.ok(updatedVaga);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        vagaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
