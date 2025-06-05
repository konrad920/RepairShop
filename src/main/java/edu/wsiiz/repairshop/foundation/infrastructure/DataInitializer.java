package edu.wsiiz.repairshop.foundation.infrastructure;

import edu.wsiiz.repairshop.auth.domain.user.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final String[] defaultPermissions;
    private final UserRole[] defaultRoles;
    private final User[] defaultUsers;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;

        this.defaultPermissions = new String[]{"READ", "WRITE", "DELETE", "UPDATE", "MANAGE_USERS"};
        this.defaultRoles = new UserRole[]{UserRole.ADMIN, UserRole.CUSTOMER, UserRole.EMPLOYEE};
        this.defaultUsers = new User[]{new User("admin", "admin", null),new User("user", "user", null)};
    }

    @Override
    public void run(String... args) throws Exception {
        Set<Permission> permissions = createPermissionIfNotExist(this.defaultPermissions);
        Set<Role> roles = createRoleIfNotExist(this.defaultRoles);
        Set<User> users = createUserIfNotExist(this.defaultUsers);

    }

    private Set<Permission> createPermissionIfNotExist(String... names) {
        Set<Permission> result = new HashSet<>();
        for (String name : names) {
            if (permissionRepository.findByName(name).isEmpty()) {
                Permission newPermission = new Permission(name);
                permissionRepository.save(newPermission);
                result.add(newPermission);
            }
        }
        return result;
    }

    private Set<Role> createRoleIfNotExist(UserRole... roleNames) {
        Set<Role> result = new HashSet<>();
        for (UserRole roleName : roleNames) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role(roleName);
                assignRolePermissions(role);
                roleRepository.save(role);
                result.add(role);
            }
        }
        return result;
    }

    private void assignRolePermissions(Role role) {
        switch (role.getName()) {
            case UserRole.ADMIN ->
                    role.setPermissions(permissionRepository.findByNameIn(Set.of("READ", "WRITE", "DELETE", "UPDATE", "MANAGE_USERS")));
            case UserRole.CUSTOMER ->
                    role.setPermissions(permissionRepository.findByNameIn(Set.of("READ", "WRITE", "DELETE", "UPDATE")));
            case UserRole.EMPLOYEE -> role.setPermissions(permissionRepository.findByNameIn(Set.of("READ")));
        }
    }

    private Set<User> createUserIfNotExist(User... users) {
        Set<User> result = new HashSet<>();
        for (User user : users) {
            if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
                if ("admin".equals(user.getUsername())) {
                    user.setRoles(Set.of(roleRepository.findByName(UserRole.ADMIN)
                            .orElseThrow(() -> new RuntimeException("ADMIN role not found"))));
                } else {
                    user.setRoles(Set.of(roleRepository.findByName(UserRole.CUSTOMER)
                            .orElseThrow(() -> new RuntimeException("CUSTOMER role not found"))));
                }
                userRepository.save(user);
                result.add(user);
            }
        }
        return result;
    }
}
