package edu.wsiiz.repairshop.auth.domain.user;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinTable(
          name = "application_user_role",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Role role;

  public User(String username,String password,Role role) {
      this.username = username;
      this.password = password;
      this.role = role;
  }
}
