package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.CarroDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.CarroRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarroService {

    private final CarroRepository carroRepository;

    @Autowired
    public CarroService(CarroRepository carroRepository){
        this.carroRepository = carroRepository;
    }

    public Page<CarroDTO> findAll (Pageable pageable){
        Page<Carro> carros = carroRepository.findAll(pageable);
        return carros.map(this::toDTO);
    }

    @Transactional(readOnly = true )
    public CarroDTO findById(String id){
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Carro não encontrada"));
        return toDTO(carro);
    }

    @Transactional
    public CarroDTO save(CarroDTO carroDTO){
        Carro carro = carroRepository.save(toEntity(carroDTO));
        return toDTO(carro);
    }

    @Transactional
    public CarroDTO update(String id, CarroDTO carroDTO){
        try{
            Carro carro = carroRepository.findById(id)
                    .orElseThrow(() -> new ControllerNotFoundException("Carro não encontrada"));

            carro.setPlaca(carroDTO.placa());
            carro.setMarca(carroDTO.marca());
            carro.setModelo(carroDTO.modelo());
            carro.setCor(carroDTO.cor());
            carro.setMotorista(carroDTO.motorista());

            carro = carroRepository.save(carro);
            return toDTO(carro);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Carro não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        carroRepository.deleteById(id);
    }

    private CarroDTO toDTO(Carro carro) {
        return new CarroDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getCor(),
                carro.getMotorista(),
                carro.getVersion()
        );
    }

    private Carro toEntity(CarroDTO carroDTO) {
        return new Carro(
                carroDTO.id(),
                carroDTO.placa(),
                carroDTO.marca(),
                carroDTO.modelo(),
                carroDTO.cor(),
                carroDTO.motorista(),
                carroDTO.version()
        );
    }
}
