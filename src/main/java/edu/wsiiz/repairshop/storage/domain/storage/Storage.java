package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "storageId")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storageId;

    private String address;

    @OneToOne(mappedBy = "storage")
    private Branch branch;

    @OneToMany(mappedBy = "storage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;
}