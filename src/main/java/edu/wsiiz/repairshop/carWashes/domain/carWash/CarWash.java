package edu.wsiiz.repairshop.carWashes.domain.carWash;

import edu.wsiiz.repairshop.carWashes.domain.city.City;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
//@EqualsAndHashCode(of = "id")
public class CarWash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    String name;

    long scheduleId;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

}
