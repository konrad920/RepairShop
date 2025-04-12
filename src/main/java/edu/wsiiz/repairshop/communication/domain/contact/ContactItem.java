package edu.wsiiz.repairshop.communication.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class ContactItem {

  @Id
  @GeneratedValue
  Long id;

  ContactPurpose purpose;

  String description;
}
