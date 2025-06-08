package edu.wsiiz.repairshop.auth.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {
    Optional<Permission> findByName(String name);
    Set<Permission> findByNameIn(Collection<String> names);
}
