package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "manufacturerId")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int manufacturerId;

    private String name;

    @OneToMany(mappedBy = "manufacturer")
    private List<Resource> resources;
}
