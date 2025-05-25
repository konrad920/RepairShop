package edu.wsiiz.repairshop.customers.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizedPersonService {
    private final AuthorizedPersonRepository repository;

    public AuthorizedPerson get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public AuthorizedPerson save(AuthorizedPerson authorizedPerson) {
        return repository.save(authorizedPerson);
    }

    public void remove(AuthorizedPerson authorizedPerson) {
        repository.delete(authorizedPerson);
    }
}
