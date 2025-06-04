package edu.wsiiz.repairshop.foundation.infrastructure;

import edu.wsiiz.repairshop.auth.domain.user.*;

import org.springframework.boot.CommandLineRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        Set<Permission> basePermissions = createBasePermissions(
//                "READ", "WRITE", "DELETE", "UPDATE", "MANAGE_USERS"
//        );
//        if (roleRepository.count() == 0) {
//            roleRepository.save(new Role(UserRole.ADMIN));
//        }
    }

    private void createBasePermissions(String... names) {
        Set<Permission> result = new HashSet<>();
        Arrays.stream(names).forEach(name -> {
            Permission permission = permissionRepository.findByName(name);
            if (permission == null) {
                permission = new Permission();
                permission.setName(name);
            }
        });
    }
}
