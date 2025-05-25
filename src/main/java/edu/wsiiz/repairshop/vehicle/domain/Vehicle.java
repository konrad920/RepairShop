package edu.wsiiz.repairshop.vehicle.domain;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.*;

//Ta klasa jest agregatem!!!
//Pozostałe klasy w pakiecie model to encje!!
//Enumy to wartości
//pakiet values zawiera również wartości

@Getter
@Setter
public class Vehicle {
    private final UUID id;
    private final VIN vin;
    private final RegistrationNumber registrationNumber;
    private final String make;
    private final String model;
    private final String chassisNumber;
    private final String bodyNumber;
    private final String engineNumber;
    private final ProductionDate productionDate;
    private final VehicleType type;
    private final List<Owner> owners = new ArrayList<>();
    private final List<InsurancePolicy> insuranceHistory = new ArrayList<>();
    private ChassisParameters chassisParameters;
    private BodyParameters bodyParameters;
    private boolean serviced = false;

    public Vehicle(UUID id, VIN vin, RegistrationNumber registrationNumber, String brand, String model,
                   String chassisNumber, String bodyNumber, String engineNumber,
                   ProductionDate productionDate, VehicleType type) {
        this.id = id;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.make = brand;
        this.model = model;
        this.chassisNumber = chassisNumber;
        this.bodyNumber = bodyNumber;
        this.engineNumber = engineNumber;
        this.productionDate = productionDate;
        this.type = type;
    }

    public void addOwner(Owner owner) {
        owners.add(owner);
    }

    public void addInsurancePolicy(InsurancePolicy policy) {
        insuranceHistory.add(policy);
    }

    public boolean canBeDeleted() {
        return !serviced;
    }

    public void markAsServiced() {
        serviced = true;
    }

    public boolean matchesSearch(String vin, String registration, String ownerLastName, String policyNumber) {
        return (vin == null || this.vin.value().equalsIgnoreCase(vin)) ||
                (registration == null || this.registrationNumber.value().equalsIgnoreCase(registration)) ||
                owners.stream().anyMatch(o -> o.name().lastName().equalsIgnoreCase(ownerLastName)) ||
                insuranceHistory.stream().anyMatch(p -> p.getNumber().value().equalsIgnoreCase(policyNumber));
    }

    public UUID id() {
        return id;
    }

}
