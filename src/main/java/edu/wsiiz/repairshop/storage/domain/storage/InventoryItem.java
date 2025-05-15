package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@Setter
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long netPrice;
    private LocalDate manufacturingDate;
    private LocalDate expirationDate;
    private int count;

    @Enumerated(EnumType.STRING)
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
}
