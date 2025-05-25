package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
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
