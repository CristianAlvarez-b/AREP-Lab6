package co.edu.escuelaing.edu.arep.secure.controller;

import co.edu.escuelaing.edu.arep.secure.model.User;
import co.edu.escuelaing.edu.arep.secure.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService authService; // Mock del servicio

    @InjectMocks
    private UserController userController; // Inyecta el mock en el controlador

    @BeforeEach
    void setUp() {
        Mockito.reset(authService); // Limpia cualquier interacción previa
    }

    @Test
    public void registerUserShouldReturnSuccessMessage() {
        // Simula el registro exitoso
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("hashedpassword");

        when(authService.registerUser("testuser", "password123")).thenReturn(mockUser);

        ResponseEntity<?> response = userController.register(Map.of("username", "testuser", "password", "password123"));

        assertEquals(200, response.getStatusCodeValue()); // Verifica que es 200 OK
        assertEquals("User registered: testuser", response.getBody()); // Verifica el mensaje de respuesta
    }


    @Test
    public void loginUserShouldReturnSuccessMessage() {
        // Simula autenticación exitosa
        when(authService.authenticate("testuser", "password123")).thenReturn(true);

        ResponseEntity<?> response = userController.login(Map.of("username", "testuser", "password", "password123"));

        assertEquals(200, response.getStatusCodeValue()); // Verifica que es 200 OK
        assertEquals("Login successful", response.getBody()); // Verifica el mensaje
    }

    @Test
    public void loginUserShouldReturnUnauthorizedOnInvalidCredentials() {
        // Simula autenticación fallida
        when(authService.authenticate("testuser", "wrongpassword")).thenReturn(false);

        ResponseEntity<?> response = userController.login(Map.of("username", "testuser", "password", "wrongpassword"));

        assertEquals(401, response.getStatusCodeValue()); // Verifica que es 401 UNAUTHORIZED
        assertEquals("Invalid credentials", response.getBody()); // Verifica el mensaje
    }
}
