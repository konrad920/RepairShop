package edu.wsiiz.repairshop.vehicle.domain;


import java.time.LocalDate;
import java.util.UUID;

public class VehicleFactory {

    /**
     * Tworzy nowy pojazd na podstawie przekazanych danych.
     * @param vin Numer VIN
     * @param registrationNumber Numer rejestracyjny
     * @param make Marka pojazdu
     * @param model Model pojazdu
     * @param chassisNumber Numer podwozia
     * @param bodyNumber Numer nadwozia
     * @param engineNumber Numer silnika
     * @param productionDate Data produkcji
     * @param type Typ pojazdu (osobowy, dostawczy, motocykl)
     * @return Utworzony pojazd
     */
    public static Vehicle createVehicle(String vin, String registrationNumber,
                                        String make, String model,
                                        String chassisNumber, String bodyNumber,
                                        String engineNumber,
                                        LocalDate productionDate,
                                        VehicleType type) {
        return new Vehicle(
                UUID.randomUUID(),
                new VIN(vin),
                new RegistrationNumber(registrationNumber),
                make,
                model,
                chassisNumber,
                bodyNumber,
                engineNumber,
                new ProductionDate(productionDate),
                type
        );
    }
}
