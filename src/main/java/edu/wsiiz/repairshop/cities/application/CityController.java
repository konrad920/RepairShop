package edu.wsiiz.repairshop.cities.application;

import edu.wsiiz.repairshop.cities.domain.City;
import edu.wsiiz.repairshop.cities.domain.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cities")
@RequiredArgsConstructor
public class CityController {
    private final CityRepository cityRepository;
    private final CityService cityService;

    @GetMapping("/all")
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.ok(cityRepository.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<City> addCity(@RequestBody City city){

        City saved = cityService.save(city);
        URI location = URI.create(String.format("cities/%d", saved.getId()));
        return ResponseEntity.created(location).body(saved);
    }

    @DeleteMapping("/delete/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId){
        try{
            cityService.delete(cityId);
            return ResponseEntity.noContent().build();
        }catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
