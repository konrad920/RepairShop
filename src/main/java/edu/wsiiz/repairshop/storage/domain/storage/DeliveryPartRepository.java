package edu.wsiiz.repairshop.storage.domain.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeliveryPartRepository extends JpaRepository<DeliveryPart, Long>, JpaSpecificationExecutor<DeliveryPart> {

}
