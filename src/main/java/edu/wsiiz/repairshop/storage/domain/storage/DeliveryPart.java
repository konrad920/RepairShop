package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(of = "deliveryPartId")
public class DeliveryPart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int deliveryPartId;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
}
