package edu.wsiiz.repairshop.payments.domain.invoice;

import edu.wsiiz.repairshop.customers.domain.customer.Customer;
import edu.wsiiz.repairshop.payments.domain.settlement.Settlement;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Customer customer;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status = InvoiceStatus.PENDING;

    private LocalDate issueDate = LocalDate.now();

    public Invoice(Customer customer) {
        this.customer = customer;
    }

    public void addItem(InvoiceItem item) {
        item.setInvoice(this);
        items.add(item);
    }

    public double getTotalGrossAmount() {
        return items.stream()
                .mapToDouble(InvoiceItem::getGrossPrice)
                .sum();
    }

    public double getTotalNetAmount() {
        return items.stream()
                .mapToDouble(InvoiceItem::getNetPrice)
                .sum();
    }

    public double getTotalVatAmount() {
        return items.stream()
                .mapToDouble(InvoiceItem::getVatAmount)
                .sum();
    }


    public void markAsPaid() {
        this.status = InvoiceStatus.PAID;
    }

    public void cancel() {
        this.status = InvoiceStatus.CANCELED;
    }

    @ManyToOne
    private Settlement settlement;

}

