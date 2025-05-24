package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.appointment.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final AppointmentRepository appointmentRepository;
    private final CarWashService carWashService;

    @GetMapping("/all")
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAll());
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
    public ResponseEntity<Appointment> addAppointment(@PathVariable Long carWashId,
                                                          @RequestBody Appointment appointment) {
        Appointment saved = appointmentService.save(carWashId, appointment);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/delete/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteById(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
