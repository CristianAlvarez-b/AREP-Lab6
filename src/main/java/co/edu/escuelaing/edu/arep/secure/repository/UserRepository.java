package co.edu.escuelaing.edu.arep.secure.repository;

import co.edu.escuelaing.edu.arep.secure.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

