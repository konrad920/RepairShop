package edu.wsiiz.repairshop.communication.domain.contact;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class ContactItem {

    @Id
    Long Id;

    ContactPurpose purpose;
}
