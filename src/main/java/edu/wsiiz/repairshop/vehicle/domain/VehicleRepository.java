package edu.wsiiz.repairshop.vehicle.domain;

import java.util.*;

public interface VehicleRepository {
    Optional<Vehicle> findById(UUID id);
    List<Vehicle> search(String vin, String registration, String ownerLastName, String policyNumber);
    void save(Vehicle vehicle);
    void delete(Vehicle vehicle);
    List<Vehicle> findAll();
}
