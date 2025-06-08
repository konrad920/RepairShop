package edu.wsiiz.repairshop.payments.application;

import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import edu.wsiiz.repairshop.payments.domain.invoice.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository repository;

    public List<Invoice> findAll() {
        return repository.findAll();
    }

    public Optional<Invoice> findById(Long id) {
        return repository.findById(id);
    }

    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    public boolean markAsPaid(Long id) {
        Optional<Invoice> invoiceOpt = repository.findById(id);
        if (invoiceOpt.isPresent()) {
            Invoice invoice = invoiceOpt.get();
            invoice.markAsPaid();
            repository.save(invoice);
            return true;
        }
        return false;
    }

    public boolean cancel(Long id) {
        return repository.findById(id)
                .map(invoice -> {
                    invoice.cancel();
                    repository.save(invoice);
                    return true;
                })
                .orElse(false);
    }

    public void remove(Invoice item) {
        repository.delete(item);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

