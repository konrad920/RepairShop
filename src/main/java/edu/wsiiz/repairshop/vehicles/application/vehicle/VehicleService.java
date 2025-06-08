package edu.wsiiz.repairshop.vehicles.application.vehicle;

import edu.wsiiz.repairshop.vehicles.domain.vehicle.Vehicle;
import edu.wsiiz.repairshop.vehicles.domain.vehicle.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public List<Vehicle> searchByVin(String vin) {
        return repository.findByVinContainingIgnoreCase(vin);
    }

    public Optional<Vehicle> getById(Long id) {
        return repository.findById(id);
    }

    public Vehicle save(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
