package ua.goit.hw18restapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.goit.hw18restapi.exception.EntityNotFoundException;
import ua.goit.hw18restapi.model.User;
import ua.goit.hw18restapi.model.dto.request.UserCreateRequest;
import ua.goit.hw18restapi.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(UserCreateRequest request) {
        if (userRepository.existsByUserName(request.userName())) {
            return "User already exists";
        }

        User user = User.builder()
                .userName(request.userName())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role("ROLE_USER")
                .build();
        userRepository.save(user);
        return "User created";
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User " + userName + " not found"));
    }
}
