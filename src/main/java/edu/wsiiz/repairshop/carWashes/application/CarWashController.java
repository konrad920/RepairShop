package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
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

    @GetMapping("/all")
    public ResponseEntity<List<CarWash>> getAllCarWashes(){
        return ResponseEntity.ok(carWashService.findAll());
    }

    @PostMapping("/add")
    public  ResponseEntity<CarWash> addCarWash(@RequestBody CarWash carWash){
        CarWash saved = carWashService.save(carWash);
        return ResponseEntity
                .created(URI.create("carWashes" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("/delete/{deleteId}")
    public ResponseEntity<Void> deleteCarWash(@PathVariable Long id){
        carWashService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}