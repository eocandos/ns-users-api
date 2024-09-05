package com.ns.users.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Pattern;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name="Users")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "userId", updatable = false, nullable = false)
  private UUID userId;

  @NaturalId
  @Column(unique = true)
  @NotEmpty
  @Pattern(regexp = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;

  @NonNull
  @Pattern(
          regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{4,12}$",
          message = "password must be min 4 and max 12 length containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit"
  )
  private String password;

  private String token;

  private String name;

  @OneToMany(cascade = CascadeType.ALL)
  private List<Phone> phones;

  private boolean isActive;

  @ElementCollection(fetch = FetchType.EAGER)
  List<UserRole> appUserRoles;

  @CreationTimestamp
  private Date created;

  @UpdateTimestamp
  private Date modified;

  @CreationTimestamp
  private Date lastLogin;
}
