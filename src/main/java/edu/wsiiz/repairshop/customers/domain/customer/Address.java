package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String type; //  "Korespondencyjny", "Siedziby"
}
