package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.PaquimetroDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.PaquimetroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paquimetro")
public class PaquimetroController {

    private PaquimetroService paquimetroService;

    @Autowired
    public PaquimetroController(PaquimetroService paquimetroService){
        this.paquimetroService = paquimetroService;
    }

    @GetMapping
    public ResponseEntity<Page<PaquimetroDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "endereco")Pageable pageable
    ) {
        Page<PaquimetroDTO> paquimetrosDTOS = paquimetroService.findAll(pageable);
        return ResponseEntity.ok(paquimetrosDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaquimetroDTO> findById (@PathVariable String id){
        PaquimetroDTO paquimetroDTO = paquimetroService.findById(id);
        return ResponseEntity.ok(paquimetroDTO);
    }

    @PostMapping
    public ResponseEntity<PaquimetroDTO> save(@Valid @RequestBody PaquimetroDTO paquimetroDTO){
        PaquimetroDTO savedPaquimetro = paquimetroService.save(paquimetroDTO);
        return new ResponseEntity<>(savedPaquimetro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<PaquimetroDTO> update(@PathVariable String id, @Valid @RequestBody PaquimetroDTO paquimetroDTO){
        PaquimetroDTO updatedPaquimetro = paquimetroService.update(id, paquimetroDTO);
        return ResponseEntity.ok(updatedPaquimetro);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        paquimetroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
