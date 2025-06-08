package edu.wsiiz.repairshop.customers.domain.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public Address get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Address save(Address address) {
        return repository.save(address);
    }

    public void remove(Address address) {
        repository.delete(address);
    }

    public List<Address> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }
}
