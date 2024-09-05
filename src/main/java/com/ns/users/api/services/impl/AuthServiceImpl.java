package com.ns.users.api.services.impl;

import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.exception.CustomException;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import com.ns.users.api.services.AuthService;
import com.ns.users.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  @Autowired
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public UserService userService;

  @Override
  public String login(User appUser) {
    try {
      String email = appUser.getEmail();
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, appUser.getPassword()));
      String token = jwtTokenProvider.createToken(email,
              userRepository.findByEmail(email).getAppUserRoles());
      userService.updateStatusUser(email, token);
      return token;
    } catch (AuthenticationException e) {
      throw new CustomException(ErrorMessages.ERROR_UNAUTHORIZED, HttpStatus.UNAUTHORIZED);
    }
  }

}
