package edu.wsiiz.repairshop.carWashes.domain.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    public List<Appointment> findByCarWashId(Long carWashId);
    public List<Appointment> findByCarWashIdAndVehicleType(Long carWashId, VehicleType vehicleType);

    @Query("SELECT a " +
            "FROM Appointment a " +
            "WHERE a.carWash.id = :carWashId " +
            "AND a.startTime < :endTime " +
            "AND a.endTime > :startTime")
    public List<Appointment> findConflictingAppointments(@Param("carWashId")Long carWashId,
                                                         @Param("startTime")LocalDateTime startTime,
                                                         @Param("endTime")LocalDateTime endTime);
}
