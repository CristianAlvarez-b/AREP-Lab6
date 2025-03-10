package co.edu.escuelaing.edu.arep.secure.controller;

import co.edu.escuelaing.edu.arep.secure.model.User;
import co.edu.escuelaing.edu.arep.secure.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> body) {
        User newUser = authService.registerUser(body.get("username"), body.get("password"));
        return ResponseEntity.ok("User registered: " + newUser.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        boolean authenticated = authService.authenticate(body.get("username"), body.get("password"));
        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
        return ResponseEntity.ok("Login successful");
    }
}
