package edu.wsiiz.repairshop.customers.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Pobiera zgody marketingowe klienta na podstawie listy identyfikatorów zgód.
     * @param consentIds Lista identyfikatorów predefiniowanych zgód marketingowych (np. "1.1", "2.2").
     * @return Lista obiektów MarketingConsentCustomer.
     */
    public List<MarketingConsentCustomer> getMarketingConsentsForCustomer(Long customerId, List<String> consentIds) {
        return customerRepository.findMarketingConsentsByIds(consentIds);
    }

    public Customer get(Long aLong) {
    }

    public Customer save(Customer customer) {
    }
}
