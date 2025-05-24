package edu.wsiiz.repairshop.carWashes.domain.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Setter
@Getter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    private WashType washType;

    @Enumerated(EnumType.STRING)
    private DirtLevel dirtLevel;

    private String notes;

    @ManyToOne
    @JoinColumn(name = "car_wash_id")
    @JsonBackReference
    private CarWash carWash;
}
