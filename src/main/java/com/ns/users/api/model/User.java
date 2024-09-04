package com.ns.users.api.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

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
  private String email;

  @NonNull
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
