package com.ns.users.api.controller;

import com.ns.users.api.dto.UserDataDTO;
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
    public ResponseEntity<List<UserDataDTO>> getUsers() {
        List<UserDataDTO> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDataDTO> register(@RequestBody UserDataDTO userDataDTO) {
        UserDataDTO registeredUserDTO = userService.register(userDataDTO);
        return ResponseEntity.ok(registeredUserDTO);
    }

}
