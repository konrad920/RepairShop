package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.storage.domain.storage.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

  private final ResourceRepository resourceRepository;
  private final DeliveryRepository deliveryRepository;
  private final DeliveryPartRepository deliveryPartRepository;

  public List<Delivery> getAllDeliveries() {
    return deliveryRepository.findAll();
  }

  public Delivery getDelivery(Long id) {
    return deliveryRepository.findById(id).orElse(null);
  }

  public List<Resource> getAllResources() {
    return resourceRepository.findAll();
  }

  public List<DeliveryPart> getAllDeliveryParts() {
    return deliveryPartRepository.findAll();
  }

  public DeliveryPart getDeliveryPart(Long id) {
    return deliveryPartRepository.findById(id).orElse(null);
  }

  public List<DeliveryPart> getPartsForDelivery(Long deliveryId) {
    Delivery delivery = deliveryRepository.findById(deliveryId)
            .orElseThrow(() -> new EntityNotFoundException("Delivery not found"));
    return delivery.getDeliveryParts();
  }

  public Delivery save(Delivery delivery) {
    return deliveryRepository.save(delivery);
  }

  public void deleteDelivery(Long deliveryId) {
    deliveryRepository.deleteById(deliveryId);
  }

  public DeliveryPart save(DeliveryPart deliveryPart) {
    Resource resource = deliveryPart.getResource();
    resource.setQuantity(resource.getQuantity() + deliveryPart.getQuantity());

    resourceRepository.save(resource);

    return deliveryPartRepository.save(deliveryPart);
  }

  public void deleteDeliveryPart(Long deliveryPartId) {
    deliveryPartRepository.deleteById(deliveryPartId);
  }
}
