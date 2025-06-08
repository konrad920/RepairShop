package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.storage.domain.storage.ResourceRepository;
import edu.wsiiz.repairshop.storage.domain.storage.Storage;
import edu.wsiiz.repairshop.storage.domain.storage.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {

    private final ResourceRepository resourceRepository;
    private final StorageRepository storageRepository;

    /**
     * Pobiera wszystkie zasoby globalnie z całego systemu (ze wszystkich magazynów).
     *
     * @return lista zasobów
     */
    @GetMapping
    public ResponseEntity<List<Resource>> getAllResources() {
        return ResponseEntity.ok(resourceRepository.findAll());
    }

    /**
     * Pobiera zasoby z konkretnego magazynu na podstawie jego ID.
     *
     * @param storageId ID magazynu
     * @return lista zasobów przypisanych do magazynu
     */
    @GetMapping("/{storageId}/resources")
    public ResponseEntity<List<Resource>> getResourcesByStorage(@PathVariable Long storageId) {
        Optional<Storage> found = storageRepository.findById(storageId);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resourceRepository.findByStorageId(storageId));
    }

    /**
     * Sprawdza dostępność zasobu w magazynie lokalnym po nazwie zasobu.
     *
     * @param name      nazwa zasobu
     * @param storageId ID magazynu
     * @return lista pasujących zasobów
     */
    @GetMapping("/{storageId}/resource/{name}")
    public ResponseEntity<List<Resource>> checkResourceAvailability(
            @PathVariable String name,
            @PathVariable Long storageId) {
        Optional<Storage> found = storageRepository.findById(storageId);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resourceRepository.findByNameAndStorage(name, storageId));
    }

    /**
     * Sprawdza dostępność zasobu w magazynie z uwzględnieniem producenta.
     *
     * @param name            nazwa zasobu
     * @param manufacturerName nazwa producenta
     * @param storageId        ID magazynu
     * @return lista pasujących zasobów
     */
    @GetMapping("/{storageId}/resource/{name}/manufacturer/{manufacturerName}")
    public ResponseEntity<List<Resource>> checkResourceAvailabilityWithManufacturer(
            @PathVariable String name,
            @PathVariable String manufacturerName,
            @PathVariable Long storageId) {
        Optional<Storage> found = storageRepository.findById(storageId);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                resourceRepository.findByNameManufacturerAndStorage(
                        name,
                        manufacturerName,
                        storageId
                )
        );
    }

    /**
     * Pobiera całkowitą ilość dostępnego zasobu w magazynie.
     *
     * @param name      nazwa zasobu
     * @param storageId ID magazynu
     * @return ilość dostępnego zasobu
     */
    @GetMapping("/{storageId}/resource/{name}/quantity")
    public ResponseEntity<Double> getResourceQuantity(
            @PathVariable String name,
            @PathVariable Long storageId) {
        Optional<Storage> found = storageRepository.findById(storageId);

        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(resourceRepository.findTotalQuantityByNameAndStorage(name, storageId));
    }
}