package edu.wsiiz.repairshop.communication.application;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/communication/contacts")
@RequiredArgsConstructor
public class ContactController {

  final ContactRepository repository;

  @PostMapping
  public ResponseEntity<Contact> addNew(@RequestBody Contact contact) {

    val saved = repository.save(contact);

    return ResponseEntity.created(null).body(saved);
  }

}
