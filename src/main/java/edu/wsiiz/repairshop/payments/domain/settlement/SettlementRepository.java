package edu.wsiiz.repairshop.payments.domain.settlement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
//    Settlement findByInvoiceId(Long invoiceId);
@Query("SELECT s FROM Settlement s JOIN s.invoices i WHERE i.id = :invoiceId")
Settlement findByInvoiceId(@Param("invoiceId") Long invoiceId);
}
