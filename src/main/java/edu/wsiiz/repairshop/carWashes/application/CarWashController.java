package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("carWashes")
@RequiredArgsConstructor
public class CarWashController {

    private final CarWashService carWashService;
    private final CarWashRepository carWashRepository;

    @GetMapping("/all")
    public ResponseEntity<List<CarWash>> getAllCarWashes(){
        return ResponseEntity.ok(carWashRepository.findAll());
    }

    @GetMapping("byId")
    public ResponseEntity<CarWash> getCarWashById(@RequestParam Long carWashId){
        CarWash carWash = carWashRepository.findById(carWashId).orElseThrow(() -> new RuntimeException("CarWash not found"));

        return ResponseEntity.ok(carWash);
    }

    @PostMapping("/add")
    public  ResponseEntity<CarWash> addCarWash(@RequestBody CarWash carWash){
        CarWash saved = carWashService.save(carWash);
        return ResponseEntity
                .created(URI.create("carWashes" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("/delete/{carWashId}")
    public ResponseEntity<Void> deleteCarWash(@PathVariable Long carWashId){
        carWashService.deleteById(carWashId);
        return ResponseEntity.noContent().build();
    }
}