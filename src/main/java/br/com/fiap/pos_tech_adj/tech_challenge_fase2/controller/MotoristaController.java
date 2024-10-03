package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.MotoristaDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.MotoristaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/motorista")
public class MotoristaController {

    private MotoristaService motoristaService;

    @Autowired
    public MotoristaController(MotoristaService MotoristaService){
        this.motoristaService = motoristaService;
    }

    @GetMapping
    public ResponseEntity<Page<MotoristaDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "id")Pageable pageable
    ) {
        Page<MotoristaDTO> motoristasDTOS = motoristaService.findAll(pageable);
        return ResponseEntity.ok(motoristasDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MotoristaDTO> findById (@PathVariable String id){
        MotoristaDTO motoristaDTO = motoristaService.findById(id);
        return ResponseEntity.ok(motoristaDTO);
    }

    @PostMapping
    public ResponseEntity<MotoristaDTO> save(@Valid @RequestBody MotoristaDTO motoristaDTO){
        MotoristaDTO savedMotorista = motoristaService.save(motoristaDTO);
        return new ResponseEntity<>(savedMotorista, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<MotoristaDTO> update(@PathVariable String id, @Valid @RequestBody MotoristaDTO motoristaDTO){
        MotoristaDTO updatedMotorista = motoristaService.update(id, motoristaDTO);
        return ResponseEntity.ok(updatedMotorista);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        motoristaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
