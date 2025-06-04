package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "resourceId")
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resourceId;

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

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "delivery_part_id", nullable = false)
    private DeliveryPart deliveryPart;

    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;
}

