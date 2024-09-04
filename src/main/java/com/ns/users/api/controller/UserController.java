package com.ns.users.api.controller;

import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.model.User;
import com.ns.users.api.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserServiceImpl userService;
    private final ModelMapper modelMapper;

    @GetMapping("/users")
    public ResponseEntity<List<User>> geUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/users")
    public User register(@RequestBody UserDataDTO user) {
        return userService.register(modelMapper.map(user, User.class));
    }

}
