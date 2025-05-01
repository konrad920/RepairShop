package edu.wsiiz.repairshop.communication.application;

import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import edu.wsiiz.repairshop.communication.domain.contact.ContactChannel;
import edu.wsiiz.repairshop.communication.domain.contact.ContactRepository;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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

    return ResponseEntity.created(URI.create("")).body(saved);
  }

  @GetMapping("/global")
  public ResponseEntity<List<Contact>> getMany() {

    return ResponseEntity.ok(repository.findAll());
  }

  @GetMapping("/channel")
  public ResponseEntity<List<Contact>> getManyInChannel(@RequestParam("channel") ContactChannel channel) {

    return ResponseEntity.ok(repository.findByChannel(channel));
  }

  @GetMapping("/customer")
  public ResponseEntity<List<Contact>> getManyForCustomer(
      @RequestParam(value = "customerId") Long customerId,
      @RequestParam(value = "status", required = false) ContactStatus status) {

    return ResponseEntity.ok(repository.findForCustomer(customerId, status));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Contact> getOne(@PathVariable("id") Long id) {

    return repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remove(@PathVariable("id") Long id) {

    Optional<Contact> existing = repository.findById(id);

    if (existing.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    repository.delete(existing.get());

    return ResponseEntity.noContent().build();
  }

}
