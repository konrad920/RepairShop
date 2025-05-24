package edu.wsiiz.repairshop.vehicle.domain;

public abstract class TechnicalParameters {
    // Dane techniczne specyficzne dla rodzaju pojazdu
}

class ChassisParameters extends TechnicalParameters {
    // Parametry techniczne dla podwozia (serwis mechaniczny)
}

class BodyParameters extends TechnicalParameters {
    // Parametry techniczne dla nadwozia (serwis blacharski)
}
