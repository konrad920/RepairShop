package edu.wsiiz.repairshop.customers.domain.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketingConsentRepository extends JpaRepository<MarketingConsent, String> {
}
