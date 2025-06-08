package edu.wsiiz.repairshop.customers.domain.customer;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorizedPersonRepository extends JpaRepository<AuthorizedPerson, Long> {
}
