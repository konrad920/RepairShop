package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "deliveryPartId")
public class DeliveryPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryPartId;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;
}
