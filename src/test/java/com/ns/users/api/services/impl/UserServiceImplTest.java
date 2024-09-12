package com.ns.users.api.services.impl;

import com.ns.users.api.config.EmailValidationConfig;
import com.ns.users.api.config.PasswordValidationConfig;
import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.model.User;
import com.ns.users.api.repository.UserRepository;
import com.ns.users.api.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

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

    @Mock
    private EmailValidationConfig emailValidationConfig;

    @Mock
    private PasswordValidationConfig passwordValidationConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Configuración de mocks para EmailValidationConfig
        when(emailValidationConfig.getEmailRegex()).thenReturn("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        // Configuración de mocks para PasswordValidationConfig
        when(passwordValidationConfig.getPasswordRegex()).thenReturn("^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$");
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

    @Test
    public void testRegister() {
        // Arrange
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setEmail("test@example.com");
        userDataDTO.setPassword("password123");

        User user = new User();
        User savedUser = new User();
        UserDataDTO savedUserDTO = new UserDataDTO();

        // Simular comportamientos
        when(userRepository.existsByEmail(userDataDTO.getEmail())).thenReturn(false);
        when(modelMapper.map(userDataDTO, User.class)).thenReturn(user);
        when(passwordEncoder.encode(userDataDTO.getPassword())).thenReturn("encodedPassword");
        when(jwtTokenProvider.createToken(userDataDTO.getEmail(), userDataDTO.getAppUserRoles())).thenReturn("generatedToken");
        when(userRepository.save(user)).thenReturn(savedUser);
        when(modelMapper.map(savedUser, UserDataDTO.class)).thenReturn(savedUserDTO);

        // Act
        UserDataDTO result = userService.register(userDataDTO);

        // Assert
        assertEquals(savedUserDTO, result);
        verify(userRepository).existsByEmail(userDataDTO.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    public void testUpdateStatusUser() {
        // Arrange
        String email = "test@example.com";
        String token = "generatedToken";
        User existingUser = new User();
        existingUser.setEmail(email);

        // Simular comportamientos
        when(userRepository.findByEmail(email)).thenReturn(existingUser);

        // Act
        userService.updateStatusUser(email, token);

        // Assert
        assertEquals(token, existingUser.getToken());
        assertNotNull(existingUser.getLastLogin());
        verify(userRepository).save(existingUser);
    }

}
