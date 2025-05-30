package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import edu.wsiiz.repairshop.cities.domain.City;
import edu.wsiiz.repairshop.cities.domain.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CarWashService {
    private final CarWashRepository carWashRepository;
    private final CityRepository cityRepository;


//    public CarWash save(CarWash carWash){
//        return carWashRepository.save(carWash);
//    }

    public CarWash save(long cityId, CarWash carWash){
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException("Miasto nie znalezione"));

        carWash.setCity(city);
        return carWashRepository.save(carWash);
    }

    public void deleteById(long id){
        if (!carWashRepository.existsById(id)) {
            throw new EntityNotFoundException("Nie znaleziono takiej myjni samochodowej");
        }
        carWashRepository.deleteById(id);
    }
}
