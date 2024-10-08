package br.com.fiap.pos_tech_adj.tech_challenge_fase2.service;

import br.com.fiap.pos_tech_adj.tech_challenge_fase2.controller.exception.ControllerNotFoundException;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.dto.CarroDTO;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Carro;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.model.Motorista;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.CarroRepository;
import br.com.fiap.pos_tech_adj.tech_challenge_fase2.repository.MotoristaRepository;
import com.mongodb.MongoCursorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CarroService {

    private final CarroRepository carroRepository;
    private final MotoristaRepository motoristaRepository;

    @Autowired
    public CarroService(CarroRepository carroRepository, MotoristaRepository motoristaRepository){
        this.carroRepository = carroRepository;
        this.motoristaRepository = motoristaRepository;
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

        carro.getMotorista().getCarros().add(carro);

        motoristaRepository.save(carro.getMotorista());

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

            Motorista motorista = motoristaRepository.findById(carro.getMotorista().getId())
                    .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

            motorista.getCarros().removeIf(x -> Objects.equals(x.getId(), id));
            motorista.getCarros().add(carro);

            motorista = motoristaRepository.save(motorista);

            carro.setMotorista(motorista);

            carro = carroRepository.save(carro);
            return toDTO(carro);
        }
        catch (MongoCursorNotFoundException e){
            throw new ControllerNotFoundException("Carro não encontrada");
        }
    }

    @Transactional
    public void delete(String id){
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Carro não encontrada"));

        Motorista motorista = motoristaRepository.findById(carro.getMotorista().getId())
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

        motorista.getCarros().removeIf(x -> Objects.equals(x.getId(), id));

        motoristaRepository.save(motorista);

        carroRepository.deleteById(id);
    }

    private CarroDTO toDTO(Carro carro) {
        return new CarroDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getMarca(),
                carro.getModelo(),
                carro.getCor(),
                carro.getMotorista().getId(),
                carro.getVersion()
        );
    }

    private Carro toEntity(CarroDTO carroDTO) {
        Motorista motorista = motoristaRepository.findById(carroDTO.idMotorista())
                .orElseThrow(() -> new ControllerNotFoundException("Motorista não encontrada"));

        return new Carro(
                carroDTO.id(),
                carroDTO.placa(),
                carroDTO.marca(),
                carroDTO.modelo(),
                carroDTO.cor(),
                motorista,
                carroDTO.version()
        );
    }
}
