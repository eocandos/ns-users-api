package com.ns.users.api.controller;

import com.ns.users.api.model.User;
import com.ns.users.api.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
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

    public ResponseEntity<List<User>> geUsers() {
        return ResponseEntity.ok(userService.getAll());
    }
}
