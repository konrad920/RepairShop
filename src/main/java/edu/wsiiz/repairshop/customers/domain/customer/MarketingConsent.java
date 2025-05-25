package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketingConsent {

    @Id
    private String id; // Klucz zgody
    private String description; // Opis zgody
    private String type; // zakładam rozróżnienie zgód  po typie: "Firma", "Osoba Fizyczna"
}
