package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AuthorizedPerson {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String role; // Np. "Reprezentant", "Odbiorca pojazdu"
}
