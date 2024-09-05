package com.ns.users.api.services.impl;

import com.ns.users.api.config.EmailValidationConfig;
import com.ns.users.api.config.PasswordValidationConfig;
import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.exception.CustomException;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import com.ns.users.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordValidationConfig passwordValidationConfig;
    @Autowired
    private EmailValidationConfig emailValidationConfig;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(User appUser) {

        validateEmail(appUser.getEmail());
        validatePassword(appUser.getPassword());

        if (userRepository.existsByEmail(appUser.getEmail())) {
            throw new CustomException(ErrorMessages.ERROR_EMAIL_ALREADY_EXIST, HttpStatus.FORBIDDEN);
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setActive(true);
        appUser.setToken(jwtTokenProvider.createToken(appUser.getEmail(), appUser.getAppUserRoles()));
        return userRepository.save(appUser);
    }

    @Override
    public void updateStatusUser(String email, String token) {
        User oldUser = userRepository.findByEmail(email);
        oldUser.setToken(token);
        oldUser.setLastLogin(new Date());
        userRepository.save(oldUser);
    }

    public void validatePassword(String password) {
        String regex = passwordValidationConfig.getPasswordRegex();
        if (!StringUtils.hasText(password) || !password.matches(regex)) {
            throw new CustomException(ErrorMessages.ERROR_PASSWORD_DOES_NOT_MEET_THE_REQUIRED_FORMAT, HttpStatus.BAD_REQUEST);
        }
    }

    public void validateEmail(String email) {
        String regex = emailValidationConfig.getEmailRegex();
        if (!StringUtils.hasText(email) || !email.matches(regex)) {
            throw new CustomException(ErrorMessages.ERROR_INCORRECT_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
        }
    }

}
