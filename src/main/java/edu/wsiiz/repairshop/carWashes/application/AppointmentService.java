package edu.wsiiz.repairshop.carWashes.application;

import edu.wsiiz.repairshop.carWashes.domain.appointment.Appointment;
import edu.wsiiz.repairshop.carWashes.domain.appointment.AppointmentRepository;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWash;
import edu.wsiiz.repairshop.carWashes.domain.carWash.CarWashRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final CarWashRepository carWashRepository;

    public Appointment save(Long carWashId, Appointment appointment) {
        List<Appointment> conficts = appointmentRepository.findConflictingAppointments(carWashId, appointment.getStartTime(), appointment.getEndTime());

        if(!conficts.isEmpty()){
            throw new IllegalArgumentException("Termin koliduje z inną wizytą.");
        }

        CarWash carWash = carWashRepository.findById(carWashId)
                .orElseThrow(() -> new EntityNotFoundException("Myjnia nie znaleziona"));

        appointment.setCarWash(carWash);
        return appointmentRepository.save(appointment);
    }

    public void deleteById(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Nie znaleziono takiego spotkania");
        }
        appointmentRepository.deleteById(id);
    }
}
