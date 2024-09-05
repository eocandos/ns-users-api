package com.ns.users.api.dto;

import com.ns.users.api.model.Phone;
import com.ns.users.api.model.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class UserDataDTO {

  @Id
  @GeneratedValue(generator = "UUID")
  @Column(name = "userId", updatable = false, nullable = false)
  private UUID userId;

  @NaturalId
  @Pattern(regexp = "^[a-zA-Z0-9_!#$%&amp;'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;

  private String password;

  private String name;

  private List<Phone> phones;

  private List<UserRole> appUserRoles;

  @CreationTimestamp
  private Date created;

  @UpdateTimestamp
  private Date modified;

  @Override
  public String toString() {
    return "User{" +
            "userId=" + userId +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            '}';
  }

}
