package com.ns.users.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "userId", updatable = false, nullable = false)
  private UUID userId;

  @NaturalId
  @Column(unique = true)
  private String email;

  private String password;

  private String token;

  private String name;

}
