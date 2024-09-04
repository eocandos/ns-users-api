package com.ns.users.api.services.impl;

import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import com.ns.users.api.services.AuthService;
import com.ns.users.api.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.ns.users.api.exception.CustomException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final UserService userService;

  @Override
  public String login(String email, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
      String token = jwtTokenProvider.createToken(email, userRepository.findByEmail(email).getAppUserRoles());
      userService.updateStatusUser(email, token);
      return token;
    } catch (AuthenticationException e) {
      throw new CustomException("Unauthorized", HttpStatus.UNAUTHORIZED);
    }
  }

  @Override
  public User checkToken(HttpServletRequest req) {
    String token = jwtTokenProvider.resolveToken(req);
    if (token != null && jwtTokenProvider.validateToken(token)) {
      return userRepository.findByEmail(jwtTokenProvider.getUsername(token));
    }
    throw new CustomException("Invalid token", HttpStatus.UNAUTHORIZED);
  }

}
