package edu.wsiiz.repairshop.payments.application;

import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;
import edu.wsiiz.repairshop.payments.domain.settlement.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettlementService {

    private final SettlementRepository repository;

    public List<Settlement> findAll() {
        return repository.findAll();
    }

    public Optional<Settlement> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Settlement> findByInvoiceId(Long invoiceId) {
        return Optional.ofNullable(repository.findByInvoiceId(invoiceId));
    }

//    public Optional<Settlement> findByInvoiceId(Invoice invoice) {
//        return Optional.ofNullable(repository.findBy(invoice));
//        return repository.findBy(invoice)
//    }

    public Settlement save(Settlement settlement) {
        return repository.save(settlement);
    }
}

