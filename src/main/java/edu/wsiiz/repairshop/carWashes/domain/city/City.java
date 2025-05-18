package edu.wsiiz.repairshop.carWashes.domain.city;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Data
public class City {

    @Id
    @GeneratedValue
    private long id;

    private String cityName;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    @JoinColumn(name = "city_id")
    List<CarWash> carWashes;
}
