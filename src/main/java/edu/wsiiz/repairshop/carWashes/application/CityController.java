package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.city.City;
import edu.wsiiz.repairshop.carWashes.domain.city.CityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carWashes/cities")
public class CityController {
    private final CityRepository cityRepository;

    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody City city) {
        if(city.getCarWashes() != null){
            for(CarWash carWash : city.getCarWashes()){
                carWash.setCity(city);
            }
        }

        City saved = cityRepository.save(city);
        return ResponseEntity.created(null).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<City>> getAllCities() {

        return ResponseEntity.ok(cityRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(@PathVariable long id) {

        return cityRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<City> deleteById(@PathVariable long id) {
        if(cityRepository.existsById(id)){
            cityRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
