package edu.wsiiz.repairshop.storage.domain.storage;

import lombok.Getter;

@Getter
public enum Product {
    FENDER("cnt"),
    BUMPER("cnt"),
    DOOR("cnt"),
    GREASE("kg"),
    COOLANT("L");

    final String unit;

    Product(String unit) {
        this.unit = unit;
    }
}