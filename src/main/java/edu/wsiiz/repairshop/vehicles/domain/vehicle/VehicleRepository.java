package edu.wsiiz.repairshop.vehicles.domain.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByVinContainingIgnoreCase(String vin);
    List<Vehicle> findByRegistrationNumberContainingIgnoreCase(String registrationNumber);
    List<Vehicle> findByBrandContainingIgnoreCase(String brand);
    // Można dodać więcej metod filtrowania
}
