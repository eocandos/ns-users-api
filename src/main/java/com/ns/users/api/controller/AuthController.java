package com.ns.users.api.controller;

import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.model.User;
import com.ns.users.api.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private AuthServiceImpl authService;
  private final ModelMapper modelMapper;

  @PostMapping("/login")
  public String login(@RequestBody UserDataDTO user) {
    return authService.login(modelMapper.map(user, User.class));
  }

}
