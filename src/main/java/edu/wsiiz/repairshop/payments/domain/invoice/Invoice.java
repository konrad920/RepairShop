package edu.wsiiz.repairshop.payments.domain.invoice;

import jakarta.persistence.*;
import edu.wsiiz.repairshop.customers.domain.customer.Customer;

import java.time.LocalDate;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate issuedDate;

    @Enumerated
    private InvoiceStatus status;

    @JoinColumn(name = "customer_id")
    @ManyToOne
    private Customer customer;

//    private Invoice product;

}
