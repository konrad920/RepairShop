package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@Entity
@EqualsAndHashCode(of = "deliveryPartId")
public class DeliveryPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPartId;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    private int quantity;

    @OneToMany(mappedBy = "deliveryPart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Resource> resources;
}
