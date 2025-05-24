package org.vehiclefile.application;

import edu.wsiiz.repairshop.vehicle.domain.Vehicle;
import edu.wsiiz.repairshop.vehicle.domain.VehicleRepository;

import java.util.*;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> listVehicles(String vin, String registration, String ownerLastName, String policyNumber) {
        return vehicleRepository.search(vin, registration, ownerLastName, policyNumber);
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void modifyVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(UUID vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new NoSuchElementException("Nie znaleziono pojazdu"));
        if (vehicle.canBeDeleted()) {
            vehicleRepository.delete(vehicle);
        } else {
            throw new IllegalStateException("Pojazd był serwisowany i nie może zostać usunięty");
        }
    }
}
