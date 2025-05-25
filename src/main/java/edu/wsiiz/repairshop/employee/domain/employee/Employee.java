package edu.wsiiz.repairshop.employee.domain.employee;

import jakarta.persistence.*;
import lombok.*;

import javax.swing.text.Position;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue
    Long id;

    String firstName;
    String lastName;
    String address;
    String email;
    String workTime;


    @Enumerated(EnumType.STRING)
    Positions position;

    @ElementCollection(targetClass = Qualifications.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_qualifications", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "qualification")
    Set<Qualifications> qualifications;


    String notes;
}