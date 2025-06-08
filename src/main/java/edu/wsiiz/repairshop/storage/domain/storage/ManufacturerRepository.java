package edu.wsiiz.repairshop.storage.domain.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {

}
