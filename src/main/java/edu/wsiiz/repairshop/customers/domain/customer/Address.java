package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Address {

    @Id
    @GeneratedValue
    private Long id;

    private String street;
    private String city;
    private String zipCode;
    private String country;
    private String type; //  "Korespondencyjny", "Siedziby"

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
