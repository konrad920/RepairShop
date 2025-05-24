package edu.wsiiz.repairshop.payments.domain.settlement;

import edu.wsiiz.repairshop.payments.domain.invoice.Invoice;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Settlement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.UNDEFINED;

    private boolean reminderSent = false;

    private LocalDate reminderDate;

    @OneToMany
    private List<Invoice> invoices = new ArrayList<>();

}

