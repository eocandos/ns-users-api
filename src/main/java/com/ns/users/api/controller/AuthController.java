package com.ns.users.api.controller;

import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.services.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  @Autowired
  private AuthServiceImpl authService;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody UserDataDTO userDataDTO) {
    String token = authService.login(userDataDTO);
    return ResponseEntity.ok(token);
  }

}
