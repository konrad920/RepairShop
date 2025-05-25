package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
