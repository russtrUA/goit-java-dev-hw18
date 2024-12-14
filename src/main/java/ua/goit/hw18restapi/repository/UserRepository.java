package ua.goit.hw18restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.hw18restapi.model.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
}
