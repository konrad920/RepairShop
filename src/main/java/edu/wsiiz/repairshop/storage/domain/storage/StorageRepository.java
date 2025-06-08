package edu.wsiiz.repairshop.storage.domain.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StorageRepository extends JpaRepository<Storage, Long>, JpaSpecificationExecutor<Storage> {

}
