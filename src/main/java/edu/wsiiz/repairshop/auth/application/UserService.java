package edu.wsiiz.repairshop.auth.application;

import edu.wsiiz.repairshop.auth.domain.user.*;
import edu.wsiiz.repairshop.communication.domain.contact.Contact;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository repository;
    final RoleRepository roleRepository;

    public User get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void remove(User user) {
        repository.delete(user);
    }

    public User findByUsername(String username) {
        return repository.findByUsername(username).orElse(null);
    }

    public Role findOrCreateRole(UserRole roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }

}
