package edu.wsiiz.repairshop.carWashes.domain.carWash;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.wsiiz.repairshop.carWashes.domain.city.City;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Data
public class CarWash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private long scheduleId;

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonBackReference
    City city;

}
