package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarWashService {
    private final CarWashRepository carWashRepository;

    public List<CarWash> findAll(){
        return carWashRepository.findAll();
    }

    public Optional<CarWash> findById(long id){
        return carWashRepository.findById(id);
    }

    public CarWash save(CarWash carWash){
        return carWashRepository.save(carWash);
    }

    public void deleteById(long id){
        carWashRepository.deleteById(id);
    }
}
