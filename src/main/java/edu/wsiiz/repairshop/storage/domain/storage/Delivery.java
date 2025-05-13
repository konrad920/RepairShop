package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode
public class Delivery {

    @Id
    @GeneratedValue
    Long id;

    LocalDate deliveryDate;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventoryItem_id")
    InventoryItem inventoryItem;
}
