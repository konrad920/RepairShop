package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private LocalDate productionDate;

    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    private int quantity;

    @OneToMany(mappedBy = "resource", cascade = CascadeType.ALL)
    private List<DeliveryPart> deliveryParts;

    @ManyToOne
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;
}

