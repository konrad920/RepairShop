package edu.wsiiz.repairshop.communication.application;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

  final ContactRepository repository;

  public Contact get(Long id) {
    return repository.findById(id).orElse(null);
  }

  public Contact save(Contact contact) {
    return repository.save(contact);
  }

  public void remove(Contact contact) {
    repository.delete(contact);
  }

}
