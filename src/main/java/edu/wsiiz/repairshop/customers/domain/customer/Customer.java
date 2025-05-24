package edu.wsiiz.repairshop.customers.domain.customer;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
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
  private CustomerTypeTEMP customerType;
  private boolean active = true; // Status: aktywny/nieaktywny

  @OneToMany
  private List<Address> addresses; // ADDRESS TEMP
  @OneToMany
  private List<MarketingConsentCustomer> marketingConsents; // ZGODY MARKETINGOWE TEMP
  @ManyToMany
  private List<AuthorizedPerson> authorizedPeople; // OSOBY UPOWAŻNIONE TEMP



}
