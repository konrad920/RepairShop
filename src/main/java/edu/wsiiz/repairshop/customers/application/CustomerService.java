package edu.wsiiz.repairshop.customers.application;

import edu.wsiiz.repairshop.customers.domain.customer.Customer;
import edu.wsiiz.repairshop.customers.domain.customer.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    final CustomerRepository repository;

    public Customer get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public void remove(Customer customer) {
        repository.delete(customer);
    }
}
