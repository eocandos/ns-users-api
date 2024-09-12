package com.ns.users.api.services.impl;

import com.ns.users.api.config.EmailValidationConfig;
import com.ns.users.api.config.PasswordValidationConfig;
import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.exception.CustomException;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import com.ns.users.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    @Autowired
    private PasswordValidationConfig passwordValidationConfig;
    @Autowired
    private EmailValidationConfig emailValidationConfig;

    @Override
    public List<UserDataDTO> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDataDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDataDTO register(UserDataDTO userDataDTO) {

        validateEmail(userDataDTO.getEmail());
        validatePassword(userDataDTO.getPassword());

        if (userRepository.existsByEmail(userDataDTO.getEmail())) {
            throw new CustomException(ErrorMessages.ERROR_EMAIL_ALREADY_EXIST, HttpStatus.FORBIDDEN);
        }

        User user = modelMapper.map(userDataDTO, User.class);
        user.setPassword(passwordEncoder.encode(userDataDTO.getPassword()));
        user.setToken(createTokenForUser(userDataDTO));
        user.setActive(true);

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDataDTO.class);
    }

    @Override
    public void updateStatusUser(String email, String token) {
        User oldUser = userRepository.findByEmail(email);
        oldUser.setToken(token);
        oldUser.setLastLogin(new Date());
        userRepository.save(oldUser);
    }

    private void validatePassword(String password) {
        String regex = passwordValidationConfig.getPasswordRegex();
        if (!StringUtils.hasText(password) || !password.matches(regex)) {
            throw new CustomException(ErrorMessages.ERROR_PASSWORD_DOES_NOT_MEET_THE_REQUIRED_FORMAT, HttpStatus.BAD_REQUEST);
        }
    }

    private void validateEmail(String email) {
        String regex = emailValidationConfig.getEmailRegex();
        if (!StringUtils.hasText(email) || !email.matches(regex)) {
            throw new CustomException(ErrorMessages.ERROR_INCORRECT_EMAIL_FORMAT, HttpStatus.BAD_REQUEST);
        }
    }

    private String createTokenForUser(UserDataDTO userDataDTO) {
        return jwtTokenProvider.createToken(userDataDTO.getEmail(), userDataDTO.getAppUserRoles());
    }

}
