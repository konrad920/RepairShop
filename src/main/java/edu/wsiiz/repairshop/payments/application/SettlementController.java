package edu.wsiiz.repairshop.payments.application;

import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/payments/settlements")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService service;

    @GetMapping
    public ResponseEntity<List<Settlement>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Settlement> getOne(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Settlement> getByInvoice(@PathVariable Long invoiceId) {
        return service.findByInvoiceId(invoiceId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Settlement> create(@RequestBody Settlement settlement) {
        Settlement saved = service.save(settlement);
        return ResponseEntity.created(URI.create("/payments/settlements/" + saved.getId()))
                .body(saved);
    }
}

