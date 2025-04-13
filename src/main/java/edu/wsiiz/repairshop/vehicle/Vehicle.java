package edu.wsiiz.repairshop.vehicle;

public class Vehicle {
    int vehicleId;
    String registrationNumber;
    MakeDictionary make;
    ModelDictionary model;
    String vin;
    String chasisNumber;
    String bodyNumber;
    String engineNumber;
    Date productionDate;
    addVehicle();
    editVehicle();
    removeVehicle();
    searchByVin();
    searchByRegistrationNumber();
}
