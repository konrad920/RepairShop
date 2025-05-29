package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.appointment.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    //private final CarWashService carWashService;

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentRepository.findAll());
    }

    @GetMapping("allByCarWashId/{carWashId}")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByCarWashId(@PathVariable Long carWashId, @RequestParam(required = false) VehicleType vehicleType) {
        if (vehicleType != null) {
            return ResponseEntity.ok(
                    appointmentRepository.findByCarWashIdAndVehicleType(carWashId, vehicleType));
        }
        return ResponseEntity.ok(
                appointmentRepository.findByCarWashId(carWashId));
    }

    @PostMapping("/add/{carWashId}")
    public ResponseEntity<?> addAppointment(@PathVariable Long carWashId,
                                            @Validated @RequestBody Appointment appointment) {

        try {
            Appointment saved = appointmentService.save(carWashId, appointment);
            URI location = URI.create(String.format("/appointments/%d", saved.getId()));
            return ResponseEntity.created(location).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long appointmentId) {
        try {
            appointmentService.deleteById(appointmentId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
