package com.ns.users.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@NoArgsConstructor
public class User {

  private UUID userId;

  private String email;

  private String password;

  private String token;

  private String name;

}
