package com.ns.users.api.services.impl;

import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ModelMapper modelMapper;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() {
        // Arrange
        User user = new User();
        UserDataDTO userDataDTO = new UserDataDTO();

        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        when(modelMapper.map(user, UserDataDTO.class)).thenReturn(userDataDTO);

        // Act
        List<UserDataDTO> result = userService.getAll();

        // Assert
        assertEquals(1, result.size());
        assertEquals(userDataDTO, result.get(0));
    }
}