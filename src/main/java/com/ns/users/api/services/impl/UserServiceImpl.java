package com.ns.users.api.services.impl;

import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.exception.CustomException;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User register(User appUser) {
        if (!userRepository.existsByEmail(appUser.getEmail())) {
            appUser.setActive(true);
            return userRepository.save(appUser);
        } else {
            throw new CustomException(ErrorMessages.ERROR_EMAIL_ALREADY_EXIST, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void updateStatusUser(String email, String token) {
        User oldUser = userRepository.findByEmail(email);
        oldUser.setToken(token);
        oldUser.setLastLogin(new Date());
        userRepository.save(oldUser);
    }

}
