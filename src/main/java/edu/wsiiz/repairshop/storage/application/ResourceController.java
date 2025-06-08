package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.storage.domain.storage.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/resource")
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceRepository resourceRepository;
  private final ResourceService resourceService;

  /**
   * Tworzy lub aktualizuje zasób.
   *
   * @param resource zasób do zapisania
   * @return zapisany zasób
   */
  @PostMapping
  public ResponseEntity<Resource> saveResource(@RequestBody Resource resource) {
    return ResponseEntity.ok(resourceService.save(resource));
  }

  /**
   * Usuwa wskazany zasób.
   *
   * @param resourceId ID zasobu do usunięcia
   */
  @DeleteMapping("/{resourceId}")
  public ResponseEntity<Void> removeResource(@PathVariable Long resourceId) {
    Optional<Resource> found = resourceRepository.findById(resourceId);

    if (found.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    resourceService.remove(found.get());
    return ResponseEntity.noContent().build();
  }

  /**
   * Aktualizuje ilość wskazanego zasobu.
   *
   * @param resourceId ID zasobu
   * @param quantityNow nowa ilość zasobu
   */
  @PatchMapping("/{resourceId}/update-quantity")
  public ResponseEntity<Void> updateResourceQuantity(
          @PathVariable Long resourceId, @RequestParam int quantityNow) {
    Optional<Resource> found = resourceRepository.findById(resourceId);

    if (found.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    resourceService.updateResourceQuantity(found.get(), quantityNow);
    return ResponseEntity.ok().build();
  }

  /**
   * Obsługuje zużycie zasobu; usuwa go, jeśli wyczerpane są zapasy.
   *
   * @param resourceId ID zasobu
   */
  @PatchMapping("/{resourceId}/consume-and-remove")
  public ResponseEntity<Void> handleConsumptionAndRemoveIfEmpty(@PathVariable Long resourceId) {
    Optional<Resource> found = resourceRepository.findById(resourceId);

    if (found.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    resourceService.handleConsumptionAndRemoveIfEmpty(found.get());
    return ResponseEntity.ok().build();
  }
}