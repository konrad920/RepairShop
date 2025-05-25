package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = "resourceId")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceId;

    private String name;

    @Enumerated(EnumType.STRING)
    private ResourceType unit;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    private double netPrice;

    @Temporal(TemporalType.DATE)
    private Date productionDate;

    @Temporal(TemporalType.DATE)
    private Date expirationDate;

    private Double quantity;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryPart> deliveryParts;

    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;
}

