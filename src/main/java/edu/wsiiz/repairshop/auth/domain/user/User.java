package edu.wsiiz.repairshop.auth.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "APPLICATION_USER")
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue
  Long id;

  @Column(nullable = false, unique = true)
  String username;

  @Column(nullable = false)
  String password;

  @Enumerated(EnumType.STRING)
  UserRole role;
}
