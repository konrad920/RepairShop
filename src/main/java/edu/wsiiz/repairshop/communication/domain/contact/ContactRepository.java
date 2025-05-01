package edu.wsiiz.repairshop.communication.domain.contact;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContactRepository extends JpaRepository<Contact, Long> {

  List<Contact> findByChannel(ContactChannel channel);

  @Query("select c from Contact c where c.customerId = :customerId and (:status is null or c.status = :status)")
  List<Contact> findForCustomer(@Param("customerId") Long customerId, @Param("status") ContactStatus status);

}
