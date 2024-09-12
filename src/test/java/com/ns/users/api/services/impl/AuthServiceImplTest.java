package com.ns.users.api.services.impl;

import com.ns.users.api.constants.ErrorMessages;
import com.ns.users.api.dto.UserDataDTO;
import com.ns.users.api.exception.CustomException;
import com.ns.users.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginAuthenticationFailure() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        UserDataDTO userDataDTO = new UserDataDTO();
        userDataDTO.setEmail(email);
        userDataDTO.setPassword(password);

        // Simular fallo en la autenticación
        doThrow(new AuthenticationException("Authentication failed") {}).when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act & Assert
        CustomException thrown = assertThrows(CustomException.class, () -> authService.login(userDataDTO));
        assertEquals(ErrorMessages.ERROR_UNAUTHORIZED, thrown.getMessage());
        assertEquals(HttpStatus.UNAUTHORIZED, thrown.getStatus());
    }

}
