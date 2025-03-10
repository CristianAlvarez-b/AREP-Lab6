package co.edu.escuelaing.edu.arep.secure.service;

import co.edu.escuelaing.edu.arep.secure.model.User;
import co.edu.escuelaing.edu.arep.secure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword) {
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        return userRepository.save(newUser);
    }

    public boolean authenticate(String username, String rawPassword) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(rawPassword, user.get().getPassword());
    }
}
