package com.ns.users.api.dto;

import lombok.Data;

@Data
public class UserResponseDTO {

  private Integer id;

  private String email;

  // List<UserRole> appUserRoles;

}
