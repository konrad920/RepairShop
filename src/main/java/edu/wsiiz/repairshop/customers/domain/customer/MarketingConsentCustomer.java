package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class MarketingConsentCustomer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consent_id")
    private MarketingConsent marketingConsent; // Powiązanie ze słownikiem zgód

    private LocalDate consentDate; // Data udzielenia zgody
    private boolean granted; // Czy zgoda została udzielona (true/false)
}
