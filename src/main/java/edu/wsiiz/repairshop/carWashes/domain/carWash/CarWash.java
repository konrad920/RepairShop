package edu.wsiiz.repairshop.carWashes.domain.carWash;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.wsiiz.repairshop.carWashes.domain.appointment.Appointment;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Data
public class CarWash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String phoneNumber;

    @OneToMany(mappedBy = "carWash", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Appointment> appointments = new ArrayList<>();

}