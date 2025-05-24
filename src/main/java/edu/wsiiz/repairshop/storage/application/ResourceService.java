package edu.wsiiz.repairshop.storage.application;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.storage.domain.storage.Resource;
import edu.wsiiz.repairshop.storage.domain.storage.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResourceService {

  final ResourceRepository repository;

  public Resource save(Resource resource) {
    return repository.save(resource);
  }

  public void remove(Resource resource) {
    repository.delete(resource);
  }

}
