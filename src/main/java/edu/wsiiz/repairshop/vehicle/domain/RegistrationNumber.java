package edu.wsiiz.repairshop.vehicle.domain;

public record RegistrationNumber(String value) {
    public static record VIN(String value) {
        public VIN {
            if (value == null || value.length() != 17)
                throw new IllegalArgumentException("Nieprawidłowy numer VIN");
        }
    }
}
