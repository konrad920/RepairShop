package edu.wsiiz.repairshop.customers.domain.customer;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;


@Builder
@Service
@RequiredArgsConstructor
public class MarketingConsentService {
    private final MarketingConsentRepository marketingConsentRepository;

    @PostConstruct
    public void init() {
        // Zahardkodowane przykładowe zgody
        if (marketingConsentRepository.count() == 0) {
            marketingConsentRepository.save(MarketingConsent.builder().id("1.1").description("RODO dla firm").type("Firma").build());
            marketingConsentRepository.save(MarketingConsent.builder().id("1.2").description("Zgody marketingowe dla firm").type("Firma").build());
            marketingConsentRepository.save(MarketingConsent.builder().id("2.1").description("RODO dla osoby fizycznej").type("Osoba Fizyczna").build());
            marketingConsentRepository.save(MarketingConsent.builder().id("2.2").description("Zgody marketingowe dla osoby fizycznej").type("Osoba Fizyczna").build());
        }
    }

    public List<MarketingConsent> getAllConsents() {
        return marketingConsentRepository.findAll();
    }

    public MarketingConsent getConsentById(String id) {
        return marketingConsentRepository.findById(id).orElse(null);
    }
}
