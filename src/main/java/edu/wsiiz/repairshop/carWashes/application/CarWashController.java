package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("carWashes/carWashes")
@RequiredArgsConstructor
public class CarWashController {

    private final CarWashRepository carWashRepository;

    @PostMapping
    public ResponseEntity<CarWash> addCarWash (@RequestBody CarWash carWash){

        val saved = carWashRepository.save(carWash);

        return ResponseEntity.created(null).body(saved);
    }

    @GetMapping
    public ResponseEntity<List<CarWash>> getAllCarWashes(){

        return ResponseEntity.ok(carWashRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarWash> getCarWashById(@PathVariable long id){
        return carWashRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CarWash> deleteCarWashById(@PathVariable long id){
        if(carWashRepository.existsById(id)){
            carWashRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
