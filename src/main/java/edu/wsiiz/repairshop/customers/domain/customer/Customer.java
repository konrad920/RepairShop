package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Customer {

  @Id
  @GeneratedValue
  Long id;

  private String firstName;
  private String lastName;
  private String pesel;
  private String regon;
  private String companyName;
  private String vehicleRegistrationNumber;
  private CustomerType customerType;
  private boolean active = true; // Status: aktywny/nieaktywny

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "customer_id")
  private List<Address> addresses;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "customer_id")
  private List<MarketingConsentCustomer> marketingConsents;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
          name = "customer_authorized_person",
          joinColumns = @JoinColumn(name = "customer_id"),
          inverseJoinColumns = @JoinColumn(name = "authorized_person_id")
  )
  private List<AuthorizedPerson> authorizedPeople;

}
