package edu.wsiiz.repairshop.auth.application;

import edu.wsiiz.repairshop.auth.domain.user.User;
import edu.wsiiz.repairshop.auth.domain.user.UserRepository;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository repository;

    public User get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void remove(User user) {
        repository.delete(user);
    }

}
