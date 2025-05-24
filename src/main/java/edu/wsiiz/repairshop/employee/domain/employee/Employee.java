package edu.wsiiz.repairshop.employee.domain.employee;

import edu.wsiiz.repairshop.communication.domain.contact.ContactChannel;
import edu.wsiiz.repairshop.communication.domain.contact.ContactStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.text.Position;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue
    Long id;

    String firstName;
    String lastName;
    String email;
    String address;

    @Enumerated(EnumType.STRING)
    Positions position;

//    @Enumerated(EnumType.STRING)
//    Position qualifications;

    Double workTimeFraction;

//    @ManyToOne
//    Department department;


    @Enumerated(EnumType.STRING)
    ContactChannel channel;

    @Enumerated(EnumType.STRING)
    ContactStatus status;


    @ElementCollection(targetClass = Qualifications.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "employee_qualifications", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "qualification")
    Set<Qualifications> qualifications;
}