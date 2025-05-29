package edu.wsiiz.repairshop.cities.application;

import edu.wsiiz.repairshop.cities.domain.City;
import edu.wsiiz.repairshop.cities.domain.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;

    public City save(City city){
        City saved = cityRepository.save(city);
        return cityRepository.save(city);
    }

    public void delete(Long cityId){
        if(!cityRepository.existsById(cityId)){
            throw new EntityNotFoundException("Nie ma takiego miasta");
        }

        cityRepository.deleteById(cityId);
    }
}
