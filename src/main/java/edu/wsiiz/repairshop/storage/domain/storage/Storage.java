package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@EqualsAndHashCode(of = "id")
public class Storage {

    @Id
    @GeneratedValue
    Long id;
    String location;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL)
    List<InventoryItem> inventoryItems;
}