package edu.wsiiz.repairshop.storage.domain.storage;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "manufacturerId")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manufacturerId;

    private String name;

    @OneToMany(mappedBy = "manufacturer")
    private List<Resource> resources;
}
