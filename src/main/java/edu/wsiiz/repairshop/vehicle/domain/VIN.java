package edu.wsiiz.repairshop.vehicle.domain;

public record VIN(String value) {
    public VIN {
        if (value == null || value.length() != 17)
            throw new IllegalArgumentException("Nieprawidłowy numer VIN");
    }
}