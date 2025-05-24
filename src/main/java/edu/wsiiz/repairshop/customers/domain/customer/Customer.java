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

  @Enumerated(EnumType.ORDINAL)
  CustomerType type;

  @Enumerated(EnumType.ORDINAL)
  CustomerStatus status;

  String contactNumber;

//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "customer_id")
//  List<MarketingConsents> marketingConsents;
//
//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "customer_id")
//  List<Address> adresses;
//
//  @OneToMany(cascade = CascadeType.ALL)
//  @JoinColumn(name = "customer_id")
//  List<AuthorizedPerson> authorizedPeople;
}
