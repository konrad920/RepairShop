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

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;

  public User(String username,String password,Set<Role> roles) {
      this.username = username;
      this.password = password;
      this.roles = (roles != null) ? roles : new HashSet<>();
  }

    public UserRole getFirstRole() {
        return roles != null && !roles.isEmpty()
                ? roles.iterator().next().getName()
                : null;
    }

}
