package edu.wsiiz.repairshop.carWashes.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public List<Appointment> findByCarWashId(Long carWashId);
    public List<Appointment> findByCarWashIdAndVehicleType(Long carWashId, VehicleType vehicleType);
}
