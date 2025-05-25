package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingConsent {


    @Id
    private String id; // Klucz zgody
    private String description;  // Opis
    private String type; // "Firma", "Osoba Fizyczna"
}
