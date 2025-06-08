package edu.wsiiz.repairshop.vehicles.ui.vehicle;

import edu.wsiiz.repairshop.vehicles.application.vehicle.VehicleService;
import edu.wsiiz.repairshop.vehicles.domain.vehicle.Vehicle;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService service;

    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vehicle> getAll(@RequestParam(required = false) String vin) {
        if (vin != null && !vin.isBlank()) {
            return service.searchByVin(vin);
        }
        return service.getAllVehicles();
    }

    @PostMapping
    public Vehicle save(@RequestBody Vehicle vehicle) {
        return service.save(vehicle);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
