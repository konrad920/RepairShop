package edu.wsiiz.repairshop.auth.application;

import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.auth.domain.user.UserRepository;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/auth/users")
@RequiredArgsConstructor
public class UserController {
    final UserRepository repository;
    final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<User> getOne(@PathVariable("id") Long id) {

        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> addNew(@RequestBody User user) {
        val created = service.save(user);

        return ResponseEntity.created(URI.create("")).body(created);
    }
}
