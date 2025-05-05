package edu.wsiiz.repairshop.communication.domain.contact;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Contact {

  @Id
  @GeneratedValue
  Long id;

  Long customerId;

  LocalDate plannedDate;

  LocalDate contactDate;

  @Enumerated(EnumType.STRING)
  ContactChannel channel;

  @Enumerated(EnumType.STRING)
  ContactStatus status;

  String description;

  String note;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "contact_id")
  List<ContactItem> items;
}
