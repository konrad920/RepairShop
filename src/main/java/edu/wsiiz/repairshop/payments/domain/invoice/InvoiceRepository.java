package edu.wsiiz.repairshop.payments.domain.invoice;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>, JpaSpecificationExecutor<Invoice> {
    List<Invoice> findByCustomerId(Long customerId);

}

