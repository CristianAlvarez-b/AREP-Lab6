package co.edu.escuelaing.edu.arep.secure.service;

import co.edu.escuelaing.edu.arep.secure.model.User;
import co.edu.escuelaing.edu.arep.secure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_ShouldSaveUserWithEncodedPassword() {
        // Arrange
        String username = "testuser";
        String rawPassword = "password123";
        String encodedPassword = "hashedpassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(encodedPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        User registeredUser = userService.registerUser(username, rawPassword);

        // Assert
        assertNotNull(registeredUser);
        assertEquals(username, registeredUser.getUsername());
        assertEquals(encodedPassword, registeredUser.getPassword());

        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(rawPassword);
    }

    @Test
    void authenticate_ShouldReturnTrue_WhenCredentialsAreCorrect() {
        // Arrange
        String username = "testuser";
        String rawPassword = "password123";
        String hashedPassword = "hashedpassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(true);

        // Act
        boolean isAuthenticated = userService.authenticate(username, rawPassword);

        // Assert
        assertTrue(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(rawPassword, hashedPassword);
    }

    @Test
    void authenticate_ShouldReturnFalse_WhenUserNotFound() {
        // Arrange
        String username = "nonexistentuser";
        String rawPassword = "password123";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        boolean isAuthenticated = userService.authenticate(username, rawPassword);

        // Assert
        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticate_ShouldReturnFalse_WhenPasswordIsIncorrect() {
        // Arrange
        String username = "testuser";
        String rawPassword = "wrongpassword";
        String hashedPassword = "hashedpassword";

        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword(hashedPassword);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches(rawPassword, hashedPassword)).thenReturn(false);

        // Act
        boolean isAuthenticated = userService.authenticate(username, rawPassword);

        // Assert
        assertFalse(isAuthenticated);
        verify(userRepository, times(1)).findByUsername(username);
        verify(passwordEncoder, times(1)).matches(rawPassword, hashedPassword);
    }
}
