package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.storage.domain.storage.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

  private final ResourceRepository resourceRepository;

  /**
   * Zapisuje nowy zasób lub aktualizuje istniejący w bazie danych.
   *
   * @param resource zasób do zapisania
   * @return zapisany zasób
   */
  public Resource save(Resource resource) {
    return resourceRepository.save(resource);
  }

  /**
   * Usuwa zasób z bazy danych.
   *
   * @param resource zasób do usunięcia
   */
  public void remove(Resource resource) {
    resourceRepository.delete(resource);
  }

  /**
   * Aktualizuje ilość danego zasobu w wybranym magazynie.
   *
   * @param resource zasób
   * @param quantityNow nowa ilość zasobu
   */
  public void updateResourceQuantity(Resource resource, Double quantityNow) {
    resource.setQuantity(quantityNow);
    resourceRepository.save(resource);
  }

  /**
   * Obsługuje usuwanie zasobu, jeśli cała ilość została wykorzystana.
   *
   * @param resource zasób
   */
  public void handleConsumptionAndRemoveIfEmpty(Resource resource) {
    if (resource.getQuantity() != null && resource.getQuantity() <= 0) {
      resourceRepository.delete(resource);
    }
  }
}
