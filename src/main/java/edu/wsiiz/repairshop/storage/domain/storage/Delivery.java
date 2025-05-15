package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deliveryDate;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryItem> inventoryItems;

    public Delivery() {}

    public Delivery(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}