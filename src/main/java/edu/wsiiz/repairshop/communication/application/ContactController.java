package edu.wsiiz.repairshop.communication.application;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

  @GetMapping
  public ResponseEntity<List<Contact>> getMany() {

    return ResponseEntity.ok(repository.findAll());
  }

}
