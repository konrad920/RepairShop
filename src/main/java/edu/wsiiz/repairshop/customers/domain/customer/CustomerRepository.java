package edu.wsiiz.repairshop.customers.domain.customer;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


        // Metoda do wyszukiwania zgód marketingowych po ich kluczach (id z MarketingConsent)
        @Query("SELECT mcc FROM MarketingConsentCustomer mcc JOIN mcc.marketingConsent mc WHERE mc.id IN :consentIds")
        List<MarketingConsentCustomer> findMarketingConsentsByIds(@Param("consentIds") List<String> consentIds);


//        @Modifying
//        @Query("UPDATE customer c SET c.isActive = false WHERE c.id = :id")
//        void deactivateCustomer2(@Param("id") Long customerId);

        @Modifying
        @Transactional
        @Query("UPDATE Customer c SET c.isActive = false WHERE c.id = :id")
        void deactivateCustomer(@Param("id") Long id);


}
