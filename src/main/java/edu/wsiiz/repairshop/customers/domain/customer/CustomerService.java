package edu.wsiiz.repairshop.customers.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository; // Wstrzyknięcie repozytorium klientów

    /**
     * Pobiera klienta po jego identyfikatorze.
     * @param id Identyfikator klienta.
     * @return Obiekt klienta lub null, jeśli nie znaleziono.
     */
    public Customer get(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    /**
     * Zapisuje lub aktualizuje klienta w bazie danych.
     * @param customer Obiekt klienta do zapisania.
     * @return Zapisany lub zaktualizowany obiekt klienta.
     */
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    /**
     * Usuwa klienta lub dezaktywuje jego kartotekę z opcjonalną anonimizacją,
     * w zależności od tego, czy klient jest właścicielem pojazdu.
     * @param customer Obiekt klienta do usunięcia/dezaktywacji.
     */
    public void remove(Customer customer) {
        // Logika dezaktywacji zamiast usuwania, jeśli klient posiada pojazd.
        // Zgodnie z wymaganiem: "usunięcie danych klienta (jeśli nie jest właścicielem żadnego pojazdu)
        // lub dezaktywacja kartoteki klienta wraz z opcjonalną anonimizacją."
        if (customer.getVehicleRegistrationNumber() != null && !customer.getVehicleRegistrationNumber().isEmpty()) {
            customer.setActive(false); // Dezaktywacja [cite: 161]
            // Anonimizacja danych (opcjonalnie, zgodnie z wymaganiami)
            customer.setPesel(null);
            customer.setRegon(null);
            customer.setFirstName("Anonim");
            customer.setLastName("Anonim");
            customer.setCompanyName("Anonim");
            // Można rozszerzyć o bardziej zaawansowaną anonimizację adresów, zgód itp.
            customerRepository.save(customer);
        } else {
            customerRepository.delete(customer);
        }
    }

    /**
     * Wyszukuje klientów na podstawie różnych kryteriów.
     * Ta metoda używa prostych zapytań z repozytorium. W bardziej złożonych scenariuszach
     * można użyć JpaSpecificationExecutor do budowania dynamicznych zapytań.
     *
     * @param query Wyszukiwany ciąg znaków (dla imienia/nazwiska/nazwy firmy).
     * @param city Miejscowość adresu.
     * @param street Ulica adresu.
     * @param pesel Numer PESEL.
     * @param regon Numer REGON.
     * @param vehicleRegistrationNumber Numer rejestracyjny pojazdu.
     * @param activeStatus Status aktywności klienta (true/false).
     * @return Lista klientów spełniających kryteria.
     */
    public List<Customer> searchCustomers(String query, String city, String street, String pesel, String regon, String vehicleRegistrationNumber, Boolean activeStatus) {
        if (query != null && !query.isEmpty()) {
            return customerRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(query, query);
        } else if (pesel != null) {
            return customerRepository.findByPesel(pesel);
        } else if (regon != null) {
            return customerRepository.findByRegon(regon);
        } else if (vehicleRegistrationNumber != null) {
            return customerRepository.findByVehicleRegistrationNumber(vehicleRegistrationNumber);
        } else if (activeStatus != null) {
            return customerRepository.findByActive(activeStatus);
        }
        return customerRepository.findAll(); // Jeśli brak kryteriów, zwróć wszystkich klientów
    }

    /**
     * Pobiera zgody marketingowe klienta na podstawie listy identyfikatorów zgód.
     * @param consentIds Lista identyfikatorów predefiniowanych zgód marketingowych (np. "1.1", "2.2").
     * @return Lista obiektów MarketingConsentCustomer.
     */
    public List<MarketingConsentCustomer> getMarketingConsentsForCustomer(Long customerId, List<String> consentIds) {
        return customerRepository.findMarketingConsentsByIds(consentIds);
    }
}
