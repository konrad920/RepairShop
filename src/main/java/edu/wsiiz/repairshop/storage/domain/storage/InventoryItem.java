package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
public class InventoryItem {
    @Id
    @GeneratedValue
    Long id;
    String name;
    Long netPrice;
    LocalDate manufacturingDate;
    LocalDate expirationDate;
    LocalDate deliveryDate;
    int count;

    @Enumerated(EnumType.STRING)
    Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    Storage storage;

    @OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL)
    List<Delivery> deliveries;
}
