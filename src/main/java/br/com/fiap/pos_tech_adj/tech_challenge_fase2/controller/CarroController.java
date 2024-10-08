package br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.CarroDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.service.CarroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carro")
public class CarroController {

    private CarroService carroService;

    @Autowired
    public CarroController(CarroService carroService){
        this.carroService = carroService;
    }

    @GetMapping
    public ResponseEntity<Page<CarroDTO>> findAll(
            @PageableDefault(size = 10, page = 0, sort = "marca")Pageable pageable
    ) {
        Page<CarroDTO> carrosDTOS = carroService.findAll(pageable);
        return ResponseEntity.ok(carrosDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroDTO> findById (@PathVariable String id){
        CarroDTO carroDTO = carroService.findById(id);
        return ResponseEntity.ok(carroDTO);
    }

    @PostMapping
    public ResponseEntity<CarroDTO> save(@Valid @RequestBody CarroDTO carroDTO){
        CarroDTO savedCarro = carroService.save(carroDTO);
        return new ResponseEntity<>(savedCarro, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<CarroDTO> update(@PathVariable String id, @Valid @RequestBody CarroDTO carroDTO){
        CarroDTO updatedCarro = carroService.update(id, carroDTO);
        return ResponseEntity.ok(updatedCarro);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> delete(@PathVariable String id){
        carroService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
