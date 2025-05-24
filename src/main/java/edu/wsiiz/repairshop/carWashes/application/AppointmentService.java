package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.appointment.Appointment;
import edu.wsiiz.repairshop.carWashes.domain.appointment.AppointmentRepository;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CarWashRepository carWashRepository;

    public Appointment save(Long carWashId, Appointment appointment) {
        CarWash carWash = carWashRepository.findById(carWashId)
                .orElseThrow(() -> new RuntimeException("CarWash not found"));

        appointment.setCarWash(carWash);
        return appointmentRepository.save(appointment);
    }

    public void deleteById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Appointment not found");
        }
        appointmentRepository.deleteById(id);
    }
}
