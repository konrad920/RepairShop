package edu.wsiiz.repairshop.storage.domain.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    // Ta metoda pozwala pracownikowi wyszukać części według nazwy w określonym magazynie.
    @Query("SELECT r FROM Resource r WHERE r.name = :name AND r.storage.id = :storageId")
    List<Resource> findByNameAndStorage(@Param("name") String name, @Param("storageId") Long storageId);

    // Wyszukiwanie zasobów z podaniem producenta, nazwy części i magazynu.
    @Query("SELECT r FROM Resource r WHERE r.name = :name AND r.manufacturer.name = :manufacturerName AND r.storage.id = :storageId")
    List<Resource> findByNameManufacturerAndStorage(
            @Param("name") String name,
            @Param("manufacturerName") String manufacturerName,
            @Param("storageId") Long storageId
    );

    // Oblicza całkowitą ilość zasobu danego typu w magazynie.
    @Query("SELECT SUM(r.quantity) FROM Resource r WHERE r.name = :name AND r.storage.id = :storageId")
    Double findTotalQuantityByNameAndStorage(@Param("name") String name, @Param("storageId") Long storageId);

    // Pobiera wszystkie zasoby dla wskazanego magazynu.
    @Query("SELECT r FROM Resource r WHERE r.storage.id = :storageId")
    List<Resource> findByStorageId(@Param("storageId") Long storageId);
}
